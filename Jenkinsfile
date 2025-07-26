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

        stage('Docker Check') {
            steps {
                sh '/usr/bin/docker version'
                sh '/usr/bin/docker ps'
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
