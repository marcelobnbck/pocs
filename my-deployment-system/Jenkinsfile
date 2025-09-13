pipeline {
  agent any

  environment {
    KUBE_NAMESPACE = "apps"
    HELM_RELEASE   = "my-app"
    HELM_CHART     = "helm-chart"
    TOFU_DIR       = "tofu"
  }

  stages {
    stage('OpenTofu Validate & Apply') {
      steps {
        dir("${TOFU_DIR}") {
          sh 'tofu init'
          sh 'tofu validate'
          sh 'tofu apply -auto-approve'
        }
      }
    }
    stage('Install Dependencies') {
      steps {
        sh 'pip install -r requirements.txt'
      }
    }
    stage('Run Unit Tests') {
      steps {
        sh 'pytest tests/'
      }
    }
    stage('Helm Deploy') {
      steps {
        sh """
          helm upgrade --install ${HELM_RELEASE} ${HELM_CHART} \
            --namespace ${KUBE_NAMESPACE} --create-namespace
        """
      }
    }
  }

  post {
    success {
      echo '✅ Deployment successful!'
    }
    failure {
      echo '❌ Pipeline failed. Check logs above.'
    }
  }
}