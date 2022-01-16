def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t malek1234/testt:3.3.3 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push malek1234/testt:3.3.3'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
