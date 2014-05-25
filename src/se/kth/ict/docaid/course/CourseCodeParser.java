package se.kth.ict.docaid.course;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CourseCodeParser {

	private static ArrayList<Course> retrieveCourseCodes(Document doc) {
		ArrayList<Course> courses = new ArrayList<Course>();
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("courseRound");
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				// System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Course course = new Course();
					course.setCode(eElement.getAttribute("courseCode"));
					course.setRound(eElement.getAttribute("roundId"));
					courses.add(course);
				}
			}
			return courses;
	}

//	private static void printNote(NodeList nodeList) {
//
//		for (int count = 0; count < nodeList.getLength(); count++) {
//
//			Node tempNode = nodeList.item(count);
//			// make sure it's element node.
//			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
//				// get node name and value
//				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
//				System.out.println("Node Value =" + tempNode.getTextContent());
//				if (tempNode.hasAttributes()) {
//					// get attributes names and values
//					NamedNodeMap nodeMap = tempNode.getAttributes();
//					for (int i = 0; i < nodeMap.getLength(); i++) {
//						Node node = nodeMap.item(i);
//						System.out.println("attr name : " + node.getNodeName());
//						System.out.println("attr value : " + node.getNodeValue());
//					}
//				}
//				if (tempNode.hasChildNodes()) {
//					// loop again if has child nodes
//					printNote(tempNode.getChildNodes());
//				}
//
//				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
//			}
//		}
//	}

	public static void main(String argv[]) {
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse("http://www.kth.se/api/kopps/v1/courseRounds/2010:2");
			ArrayList<Course> courses = retrieveCourseCodes(doc);
			for(Course course: courses) 
				System.out.println("courseCode: " + course.getCode());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		try {
//			// File file = new File("xmlFiles/2010.xml");
//			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			Document doc = dBuilder.parse("http://www.kth.se/api/kopps/v1/course/EI2405");
//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//			if (doc.hasChildNodes()) {
//				printNote(doc.getChildNodes());
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
	}
}