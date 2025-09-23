pipeline {
    agent any
    // tools {
    //     jdk 'myjdk'
    //     maven 'my_maven'
    // }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/CHIIIPLOW-922/chiiiplow-clouddrive-be.git'
            }
        }

        // stage('Check Java') {
        //     steps {
        //         sh '''
        //         which java
        //         java -version
        //         '''
        //     }
        // }

        // stage('Build') {
        //     steps {
        //         sh '''
        //         which java
        //         which mvn
        //         mvn -version
        //         mvn clean package -DskipTests
        //         '''
        //     }
        // }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-account', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker build -t chiiiplow/demo-app:latest .
                    docker push chiiiplow/demo-app:latest
                    '''
                }
            }
        }

        stage('Deploy to K8s') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig-jenkins']) {
                    sh 'kubectl apply -f k8s/deployment.yaml'
                    sh 'kubectl rollout restart deployment demo-app'
                }
            }
        }
    }
}
