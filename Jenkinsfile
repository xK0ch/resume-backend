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
                script {
                    withEnv(["SPRING_SECURITY_USERNAME=${SPRING_SECURITY_USERNAME}", "SPRING_SECURITY_PASSWORD=${SPRING_SECURITY_PASSWORD}"]) {
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