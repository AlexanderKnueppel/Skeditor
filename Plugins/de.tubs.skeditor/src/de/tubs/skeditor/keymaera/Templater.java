package de.tubs.skeditor.keymaera;

public class Templater {
	public static void main(String[] args) {
		System.out.println(getKeepDistanceTemplate());
	}
	public static String getTemplate() {
		return "Functions.\r\n" + "  %%constants%%\r\n" + "  \r\n" + "  /*B bounds() <-> (\r\n" + "  	%%bounds%%\r\n" + "  ).\r\n" + "  \r\n" + "  B initialState() <-> (\r\n"
				+ "  	%%init%%\r\n" + "  ).\r\n" + "  B assumptions() <-> (bounds() & initialState()). */\r\n" + "End.\r\n" + "\r\n" + "ProgramVariables.\r\n"
				+ "  %%variables%%\r\n" + "End.\r\n" + "\r\n" + "Problem.\r\n" + "  /*assumptions()*/\r\n" + "  %%init%%\r\n" + "  & %%precondition%%\r\n" + "->\r\n" + "[\r\n"
				+ "	/*Program*/\r\n" + "    {\r\n" + "    	%%program%%\r\n" + "    };\r\n" + "    /* dynamic */ \r\n" + "    { \r\n" + "  		%%dynamic%%\r\n" + "  	}\r\n"
				+ "](%%postcondition%%)\r\n" + "End.";
	}
	
	public static String getKeepDistanceTemplate() {
		return "Functions.\r\n" + 
				"  R B(). /* Braking force */\r\n" + 
				"  R A().  /* Maximum acceleration */\r\n" + 
				"  R ep().\r\n" + 
				"  R minDist().\r\n" + 
				"  R maxDist().\r\n" + 
				"  %%constants%% \r\n" + 
				"  \r\n" + 
				"  /* Initial states */\r\n" + 
				"  B initial() <-> (\r\n" + 
				"     B()>=5 &   B() <= 10 & A()>=0 & A() <= 4 & minDist() = 10 & maxDist() = 30 \r\n"
				+ "     %%constvalues%% \r\n" +
				"  ).\r\n" + 
				"\r\n" + 
				" HP leader ::= {\r\n" + 
				"  al:=*; ?(-B() <= al & al <= A());\r\n" + 
				"}.\r\n" + 
				"\r\n" + 
				" HP drive ::= {\r\n" + 
				"    t := 0;                                                  /* reset control cycle timer */\r\n" + 
				"    {xl'=vl, xf' = vf, vf' = af, vl'=al, t'=1 & vf >= 0 & vl >= 0 & t <= ep()}                 /* drive (not backwards v>=0)\r\n" + 
				"                                                                for at most ep time (t<=ep) until next controller run */\r\n" + 
				"  }.\r\n" + 
				"\r\n" + 
				"  /* train controller */\r\n" + 
				"  HP follow ::= {\r\n" + 
				"        ?(!(xl-xf-minDist() <= (vf*vf-vl*vl)/(2*B()) + (A()/B()+1)*(A()/2*ep()*ep()+ep()*vf)));\r\n" + 
				"        { ?vf > vl; af:=*;?(-B() <= af & af <= A()); \r\n" + 
				"        ++ \r\n" + 
				"          ?vf <= vl; af:=*;?(-B() <= af & af <= A()); }\r\n" + 
				"        \r\n" + 
				"  }.\r\n" + 
				"\r\n" + 
				"  HP safety ::= {\r\n" + 
				"        ?(xl-xf-minDist()  <= (vf*vf-vl*vl)/(2*B()) + (A()/B()+1)*(A()/2*ep()*ep()+ep()*vf));\r\n" + 
				"           af:= -B();\r\n" + 
				"  }.\r\n" + 
				"\r\n" + 
				"End.\r\n" + 
				"\r\n" + 
				"ProgramVariables. /* program variables may change their value over time */\r\n" + 
				"  %%variables%%\r\n" + 
				"End.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Problem.          /* differential dynamic logic formula */\r\n" + 
				"    %%precondition%% & vl>=0 & vf>=0 & (xl-xf) > 10 & initial()\r\n" + 
				" -> [\r\n" + 
				"      {\r\n" + 
				"        leader;\r\n" + 
				"        follow;\r\n" + 
				"        safety;\r\n" + 
				"        drive;\r\n" + 
				"      }\r\n" + 
				"    ] %%postcondition%% xl-10 > xf\r\n" + 
				"End.";
	}

	public static String getTemplateFileName() {
		return "KeYmaeraTemplate";
	}
}
