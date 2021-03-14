pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analisys') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "\"${scannerHome}/bin/sonar-scanner.bat\" -e -Dsonar.projectKey=DeployFront -Dsonar.host.url=http://192.168.1.77:9000 -Dsonar.login=68ceaeab52a319d46f767787f22aaa3d00ff5df2 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**"
                }
            }
        }
        stage ('Sonar Quality Gate') {
            steps {
                sleep(5)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage ('Deploy to TST') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat-login', path: '', url: 'http://192.168.1.77:8001/')], contextPath: 'tasks', onFailure: false, war: 'target/tasks.war'
            }
        }
        stage ('Functional Tests') {
            steps {
                bat 'mvn integration-test -Dselenium.grid.hub=http://192.168.1.77:4444/wd/hub -Dapp.baseuri=http://192.168.1.77:8001'
            }
        }
    }
}