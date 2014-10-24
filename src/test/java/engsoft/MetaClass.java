package engsoft;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import static java.lang.System.out;

enum ClassComponents { CONSTRUCTOR, FIELD, METHOD, CLASS, ALL }

public class MetaClass {
	
	public void getClassComponents(Class<?> c, String... classPart) {
	    out.format("Class:%n  %s%n%n", c.getCanonicalName());

	    Package p = c.getPackage();
	    out.format("Package:%n  %s%n%n",
		       (p != null ? p.getName() : "-- No Package --"));
	    
	    for (int i = 0; i < classPart.length; i++) {
		switch (ClassComponents.valueOf(classPart[i])) {
		case CONSTRUCTOR:
		    printMembers(c.getDeclaredConstructors(), "Constructor");
		    break;
		case FIELD:
		    printMembers(c.getDeclaredFields(), "Fields");
		    break;
		case METHOD:
		    printMembers(c.getDeclaredMethods(), "Methods");
		    break;
		case CLASS:
		    printClasses(c);
		    break;
		case ALL:
		    printMembers(c.getDeclaredConstructors(), "Constuctors");
		    printMembers(c.getDeclaredFields(), "Fields");
		    printMembers(c.getDeclaredMethods(), "Methods");
		    printClasses(c);
		    break;
		default:
		    assert false;
		}
	    }
    }

    private static void printMembers(Member[] mbrs, String s) {
	out.format("%s:%n", s);
	for (Member mbr : mbrs) {
	    if (mbr instanceof Field)
		out.format("  %s%n", ((Field)mbr).toGenericString());
	    else if (mbr instanceof Constructor)
		out.format("  %s%n", ((Constructor)mbr).toGenericString());
	    else if (mbr instanceof Method)
		out.format("  %s%n", ((Method)mbr).toGenericString());
	}
	if (mbrs.length == 0)
	    out.format("  -- No %s --%n", s);
	out.format("%n");
    }

    private static void printClasses(Class<?> c) {
	out.format("Classes:%n");
	Class<?>[] clss = c.getDeclaredClasses();
	for (Class<?> cls : clss) {
	    out.format("  %s%n", cls.getCanonicalName());
	}
	if (clss.length == 0)
	    out.format("  -- No member interfaces, classes, or enums --%n");
	out.format("%n");
    }
}
