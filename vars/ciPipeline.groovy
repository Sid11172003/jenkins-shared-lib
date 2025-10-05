

[A[A



[A[A[A[A[A[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C[C
<<'EOF'
echo "Running shared ciPipeline with config: ${config}"


stage('Prepare') {
if (config.generateDockerfile) {
def tpl = libraryResource('templates/Dockerfile.tpl')
writeFile file: 'Dockerfile', text: tpl
echo 'Dockerfile generated from template'
}
}


stage('Build & Test') {
if (fileExists('pom.xml')) {
echo 'Detected Maven project'
sh './mvnw -B -DskipTests=false clean package'
junit 'target/surefire-reports/*.xml'
} else if (fileExists('package.json')) {
echo 'Detected Node.js project'
sh 'npm ci'
sh 'npm test'
} else {
echo 'No recognized build file; skipping build'
}
}


stage('Publish') {
if (config.publish) {
echo 'Publishing artifacts...'
if (config.publish == 'docker') {
sh "docker build -t ${config.dockerImage ?: 'my-app:latest'} ."
sh "echo 'docker push placeholder'"
} else {
sh "echo 'Publish step for ${config.publish}'"
}
} else {
echo 'Publish skipped'
}
}


stage('Post') {
if (config.notify) {
notify "Build finished: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
}
}
}
EOF
