pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew build -x test'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
                sh './gradlew integrationTest'
            }
        }
    }
}