# Jenkins Shared Library (example)


This repo shows a minimal shared library structure with:
- vars/ (pipeline-facing steps)
- src/ (Groovy classes)
- resources/ (templates and scripts)


Usage (in an app repo Jenkinsfile):

@Library('my-shared-lib@main') _

pipeline {
    agent any

    stages {
        stage('Build & Package') {
            steps {
                script {
                    ciPipeline publish: 'docker', generateDockerfile: true, notify: true
                }
            }
        }
    }
}
