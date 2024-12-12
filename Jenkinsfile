pipeline {
    agent any

    environment {
        BASIC_AUTH_USERNAME = credentials('resume-backend-username')
        BASIC_AUTH_PASSWORD = credentials('resume-backend-password')
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
                script {
                    withEnv(["BASIC_AUTH_USERNAME=${BASIC_AUTH_USERNAME}", "BASIC_AUTH_PASSWORD=${BASIC_AUTH_PASSWORD}"]) {
                        sh 'docker compose -f docker-compose-resume-backend-prod.yml down'
                        sh 'docker image prune -af'
                        sh 'docker compose -f docker-compose-resume-backend-prod.yml up --build -d'
                    }
                }
            }
        }
    }
}