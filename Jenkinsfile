pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = 'fleche-api'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Diagnóstico PATH') {
             steps {
                 sh 'echo $PATH'
                 sh 'ls -l $(which docker) || echo "docker não encontrado"'
            }
        }

        stage('Docker Build') {
            steps {
                 sh 'docker build -t fleche-api .'
            }
        }

        stage('Docker') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
                sh 'docker run --rm $IMAGE_NAME'
            }
        }
    }
}
