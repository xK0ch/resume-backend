pipeline {
    agent any

    triggers {
        pollSCM('H * * * *')
    }

    environment {
        SPRING_SECURITY_USERNAME = credentials('resume-backend-username')
        SPRING_SECURITY_PASSWORD = credentials('resume-backend-password')
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

        stage('Deploy') {
            steps {
                sh 'docker compose -f docker-compose-resume-backend.yml down'
                sh 'docker image prune -af'
                sh 'docker compose -f docker-compose-resume-backend.yml up --build -d'
            }
        }
    }
}