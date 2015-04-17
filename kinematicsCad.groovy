//Create the kinematics model from the xml file describing the D-H compliant parameters. 
DHParameterKinematics model = new DHParameterKinematics(master,"TrobotMaster.xml");

for(DHLink dh:bot.getDhChain().getLinks()){
	System.out.println("Link D-H values = "+dh);
	
}