/**
* SUBSCRIPTION MANAGER Jenkinsfile
* MAINTAINER: Luis Chacón <luisgreen at gmail dot com>
*/
def IMAGE_TAG

pipeline {
  agent { label 'code-worker' }

  options {
    timestamps()
  }

  environment {
    GCP_PROJECT_NAME  = "${env.GCP_PROJECT_NAME}"
    APP_NAME      = "backend"
  }
  stages {
    stage('Checkout Source Control') {
      steps {
        script {
            IMAGE_TAG="gcr.io/${GCP_PROJECT_NAME}/${APP_NAME}:latest"
          }
        }
    }
    stage('Building App Image') {
      steps {
        sh "docker build -t ${IMAGE_TAG} ."
      }
    }
    stage('Pushing App Image to Registry') {
      steps {
        sh "docker push ${IMAGE_TAG}"
      }
    }
  }
  post {
    success {
      script {
        echo "Success"
      }
    }
    cleanup {
      script {
        sh "docker system prune -a --force"
      }
    }
  }
}
