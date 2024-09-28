pipeline {
    agent any

    triggers {
        pollSCM('H * * * *')
    }

    environment {
        RESUME_BACKEND_USERNAME = credentials('resume-backend-username')
        RESUME_BACKEND_PASSWORD = credentials('resume-backend-password')
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
                    withEnv(["RESUME_BACKEND_USERNAME=${RESUME_BACKEND_USERNAME}", "RESUME_BACKEND_PASSWORD=${RESUME_BACKEND_PASSWORD}"]) {
                        sh """
                            docker compose -f docker-compose-resume-backend.yml down
                            docker image prune -af
                            docker compose -f docker-compose-resume-backend.yml up --build -d
                        """
                    }
                }
            }
        }
    }
}