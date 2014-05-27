package se.kth.ict.docaid.parser;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import se.kth.ict.docaid.course.Course;

/**
 * The KTH API allows retrieving the course catalog and information for a given course in XML format. This parser reads the course catalog and creates a HashMap of
 * courses. Also for a given course it reads its' XML page using the API and updates the course's content.
 * 
 * @author andrew
 * 
 */
public class CourseXMLPageParser {
	@SuppressWarnings("unused")
	private static String courseListURI = "http://www.kth.se/api/kopps/v1/courseRounds/";
	private static String coursePageURI = "http://www.kth.se/api/kopps/v1/course/";

	/**
	 * Retrieves the course codes for all courses contained in the provided XML document. The method depends on the API provided by the following url. <a
	 * href="http://www.kth.se/en/api/anvand-data-fran-kth-1.57059">API för kurs- och programinformation</a>
	 * 
	 * @param doc The document for from which the course list is extracted.
	 * @return A hash map with key the string code of the course, and value an instance of the class Course.
	 */
	public static HashMap<String, Course> retrieveCourseCodes(Document doc) {
		HashMap<String, Course> courses = new HashMap<String, Course>();
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("courseRound");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String courseCode = eElement.getAttribute("courseCode");
				int roundId = Integer.parseInt(eElement.getAttribute("roundId"));
				Course course = new Course();
				course.setCode(courseCode);
				course.setRound(roundId);
				courses.put(courseCode, course);
			}
		}
		return courses;
	}

	/**
	 * Creates a document with dom instances from an XML page. Reads the node list of the document and from each node it retrieves information about the course.
	 * 
	 * @param course The course for which information are retrieved
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void updateCourseContent(Course course) throws ParserConfigurationException, SAXException, IOException {
		// If the KTH API changes the function should also change
		String uri = constructCourseURI(course.getCode());

		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dBuilder.parse(uri);
		doc.getDocumentElement().normalize();

		retrieveCourseInformation(doc.getChildNodes(), course);
	}

	/**
	 * Constructs the uri of the XML course page, as instructed by the following link. <a href="http://www.kth.se/en/api/anvand-data-fran-kth-1.57059">API för kurs- och
	 * programinformation</a>
	 * 
	 * @param code The KTH course code.
	 * @return the URI to retrieve the XML page of a given course.
	 */
	private static String constructCourseURI(String code) {
		String uri = coursePageURI + code;
		return uri;
	}

	/**
	 * This function checks for the required data in element nodes and recursively in children nodes.
	 * 
	 * @param nodeList The list of nodes in the XML page
	 * @param course The course for which information are retrieved.
	 */
	private static void retrieveCourseInformation(NodeList nodeList, Course course) {

		for (int count = 0; count < nodeList.getLength(); count++) {
			Node node = nodeList.item(count);

			// make sure it's element node.
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				extractXMLElements(node, course);
				if (node.hasChildNodes())
					retrieveCourseInformation(node.getChildNodes(), course);
			}
		}
	}

	/**
	 * The structure of the course XML page is a parent element "course" and its child elements. This functions identifies some of the child nodes, and extracts the
	 * corresponding data. It updates the information of the course object.
	 * 
	 * @param node The xml element for which data are extracted.
	 * @param course The extracted data are stored in the corresponding course.
	 */
	private static void extractXMLElements(Node node, Course course) {
		String nodeName = node.getNodeName();

		// Course is cancelled or is active.
		if (nodeName.equals("cancelled")) {
			course.setCancelled(Boolean.parseBoolean(node.getTextContent()));
			return;
		}
		// Course title in En and Sv
		if (nodeName.equals("title")) {
			NamedNodeMap childNodes = node.getAttributes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("xml:lang")) {
					if (childNode.getNodeValue().equals("en"))
						course.setTitleEn(node.getTextContent());
					else if (childNode.getNodeValue().equals("sv"))
						course.setTitleSv(node.getTextContent());
				}
			}
			return;
		}
		// Number of credits that the course offers multiplied by 10.
		if (nodeName.equals("deciCredits")) {
			course.setCredits(Integer.parseInt(node.getTextContent()));
			return;
		}
		// Text representation of the cycle
		if (nodeName.equals("educationalLevel")) {
			NamedNodeMap childNodes = node.getAttributes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("xml:lang") && childNode.getNodeValue().equals("en"))
					course.setEducationLevel(node.getTextContent());
			}
			return;
		}
		if (nodeName.equals("academicLevel")) {
			course.setAcademicLevelCode(node.getTextContent());
			return;
		}
		// Subject code
		if (nodeName.equals("subjectCode")) {
			course.setSubjectCode(node.getTextContent());
			return;
		}
		// Text representation of the subject.
		if (nodeName.equals("subject")) {
			NamedNodeMap childNodes = node.getAttributes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("xml:lang") && childNode.getNodeValue().equals("en"))
					course.setSubject(node.getTextContent());
			}
			return;
		}
		// AF - 1-9
		if (nodeName.equals("gradeScaleCode")) {
			course.setGradeScaleCode(node.getTextContent());
			return;
		}
		// The code of the department.
		if (nodeName.equals("departmentCode")) {
			course.setDepartmentCode(node.getTextContent());
			return;
		}
		// Text representation of the department that the course is offered.
		if (nodeName.equals("department")) {
			NamedNodeMap childNodes = node.getAttributes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("xml:lang") && childNode.getNodeValue().equals("en"))
					course.setDepartment(node.getTextContent());
			}
			return;
		}
		// Information of course instructor
		if (nodeName.equals("contactName")) {
			course.setContactName(node.getTextContent());
			return;
		}
		// Description of the course, if set by course instructor.
		if (nodeName.equals("recruitmentText")) {
			NamedNodeMap childNodes = node.getAttributes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("xml:lang")) {
					if (childNode.getNodeValue().equals("en"))
						course.setRecruitmentTextEn(Jsoup.parse(node.getTextContent()).text());
					else if (childNode.getNodeValue().equals("sv"))
						course.setRecruitmentTextSv(Jsoup.parse(node.getTextContent()).text());
				}
			}
			return;
		}
		// Information about course web url
		if (nodeName.equals("supplementalInformationUrl")) {
			NamedNodeMap childNodes = node.getAttributes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("xml:lang") && childNode.getNodeValue().equals("en"))
					course.setCourseURL(node.getTextContent());
			}
		}
		return;
	}
}