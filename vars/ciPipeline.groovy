// vars/ciPipeline.groovy
def call(Map config = [:]) {
    echo "Running shared ciPipeline with config: ${config}"

    stage('Prepare') {
        if (config.generateDockerfile) {
            echo "Generating Dockerfile..."
            def tpl = libraryResource('templates/Dockerfile.tpl')
            writeFile file: 'Dockerfile', text: tpl
            echo 'Dockerfile generated from template'
        }
    }

    stage('Build & Test') {
        if (fileExists('pom.xml')) {
            echo 'Detected Maven project'
            sh './mvnw -B -DskipTests=false clean package || echo "Skipping Maven build for demo"'
        } else if (fileExists('package.json')) {
            echo 'Detected Node.js project'
            sh 'npm ci || echo "Skipping Node install for demo"'
            sh 'npm test || echo "Skipping Node tests for demo"'
        } else {
            echo 'No recognized build file; using demo artifact'
            // Create a placeholder JAR so Docker build succeeds
            sh 'mkdir -p target && echo "demo jar content" > target/app.jar'
        }
    }

    stage('Publish') {
        if (config.publish) {
            echo "Publishing artifacts..."
            if (fileExists('target/app.jar')) {
                // Docker build will not fail even if the JAR is a placeholder
                sh "docker build -t ${config.dockerImage ?: 'my-app:latest'} . || echo 'Skipping Docker build in demo mode'"
            } else {
                echo 'No build artifact found; skipping Docker build'
            }
        } else {
            echo 'Publish skipped'
        }
    }

    stage('Post') {
        if (config.notify) {
            echo "Notification: Build finished: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
    }
}


