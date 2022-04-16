def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                      echo "my my my mymy"
                      echo "my my my mymy"
                }
            }
        }
        stage('increment version') {
           steps {
             script {
                       echo "incrementing app version..."
                       sh 'mvn build-helper:parse-version versions:set  -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.nextMinorVersion}.\\\${parsedVersion.incrementalVersion}  versions:commit'
                       def matcher =readFile('pom.xml') =~ '<version>(.+)</version>'    //this will matche each version tag inside pom.xml and we need first tag 		and 		first tag insdide
                       def version =matcher[0][1]
                       env.IMAGE_NAME ="$version-$BUILD_NUMBER" // this will be the name take the tag name from insdide the POM ,than appand it with build number and save it in globalr
        										//variable        IMAGE_NAME       for docker
                                 }
                     }
              }
        stage("build jar") {
            steps {
                script {
                    echo "building jar"
                    gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"
                      echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hup-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t malek1234/testt:${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push malek1234/testt:${IMAGE_NAME}"
    }
                    
                //    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
                    gv.deployApp()
                }
            }
        }
    }   
}
