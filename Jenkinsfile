 pipeline {
     agent any
     stages {
         stage('Build') {
             steps {
                 sh 'mvn clean package -DskipTests'
             }
         }
         stage('Test') {
             steps {
                 sh 'mvn test'
             }
         }
         stage('Docker') {
             steps {
                 sh 'docker build -t ./fleche-api .'
                 sh 'docker run --rm fleche-api'
             }
         }
     }
 }
