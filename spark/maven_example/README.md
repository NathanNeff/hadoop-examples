# Readme

    # This is only to be performed on the VM, and is in no
    # way an example of a good practice

    cd ~/training_materials/sparkdev/projects/countjpgs
    mv pom.xml pom.xml.bak
    curl https://raw.githubusercontent.com/NathanNeff/hadoop-examples/master/spark/maven_example/pom.xml > pom.xml

    # to hack the local repository on the VM, remove the local repository by un-commenting this line and running
    # it
    # rm -rf ~/.m2
    mvn compile



