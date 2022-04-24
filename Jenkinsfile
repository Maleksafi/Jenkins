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
                      echo "my my my mddymy"
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
        stage('commit version update'){
steps{
script {
echo "commit version update..."
 withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
		sh 'git config --global user.email "jenkins@example.com"'	//this is just for one time if we want to set it globly to whole project we add --global 
		sh 'git config --global user.name "jenkins"' //this is just for one time 	
				//as alternative we cloud ssh to Jenkins server and add set of configurtion 
		
		sh 'git status'		//print some info for git repo before push it 
		sh 'git branch'
		sh 'git config --list'
 
		sh "git remote set-url origin https://${USER}:${PASS}@github/Maleksafi/Jenkins.git"	//address for remote repo and the USER and password this for our git which is pass it  //from withCredentials 
		sh 'git add .'
        sh 'git commit -m "ci: version bump"'
        sh 'git push origin HEAD:master'	//master the name of branch and it will git last commit in the branch
}
}
}
	}

    }   
}
