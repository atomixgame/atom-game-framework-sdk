Run
===

Run "gradle" or the gradle wrapper. This will build the sample main, assemble a jar and run it. 


Update to newest SDK version
============================

Check the sdk update path (Tools -> Plugins -> Settings)

Download these files:	

com-jme3-gde-project-baselibs.nbm	 
com-jme3-gde-project-libraries.nbm	 
com-jme3-gde-materials.nbm	 
com-jme3-gde-project-testdata.nbm

Unzip them, they have a netbeans/libs folder which contains packed jar files.

Unpack them with 
  ls | grep "\(.*\)\.pack.gz$" | xargs -I{} basename {} ".jar.pack.gz" | xargs -I{} unpack200 {}.jar.pack.gz {}.jar

And place them into the libs folder
