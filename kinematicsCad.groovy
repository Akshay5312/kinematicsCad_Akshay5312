//Create the kinematics model from the xml file describing the D-H compliant parameters. 
DHParameterKinematics model = new DHParameterKinematics(dyio,"TrobotMaster.xml");

//Creating a list of objects, one for each link
ArrayList<Object> links = new ArrayList<Object>();

for(DHLink dh : model.getDhChain().getLinks() ){
	System.out.println("Link D-H values = "+dh);
	// Create an axis to represent the link
	Axis a = new Axis(15);
	//add a sphere to make it easier to distinguish
	a.getChildren().add(new Sphere(5));
	// create a position listener object
	Affine s = new Affine();
	//add listener to link
	dh.setListener(s);
	//add listner to axis
	a.getTransforms().add(s);
	// add ax to list of objects to be returned
	links.add(a);
}

return links;