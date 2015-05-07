//Create the kinematics model from the xml file describing the D-H compliant parameters. 
DHParameterKinematics model = new DHParameterKinematics(dyio,"TrobotMaster.xml");
 
//Creating a list of objects, one for each link
ArrayList<Object> links = new ArrayList<Object>();
model.setScriptingName("DHArm")
links.add(model)
for(DHLink dh : model.getDhChain().getLinks() ){
	System.out.println("Link D-H values = "+dh);
	// Create an axis to represent the link
	CSG cube = new Cube(20).toCSG();
	//add listner to axis
	cube.setManipulator(dh.getListener());
	// add ax to list of objects to be returned
	links.add(cube);
}
 
return links;