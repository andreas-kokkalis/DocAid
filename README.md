===================================================
DocAid engine is part of the DocAid project.
DocAid can be found in: kthtest-docaid.rhcloud.com/docaid
===================================================

-----------------------------
DocAid project
-----------------------------
Extracting and Integrating Meta-data from online sources.
A set of examples leading to improved course and instructor selection for KTH Royal Institute of Technology students.

The following modules are the code of DocAid Engine.
	• Acronym extraction module for a text source - URL or uploaded document.
	• Keyword extraction module for a text source - URL or uploaded document.
	• Key phrase extraction module for a text source - URL or uploaded document.
	• Document analysis module combines the previous modules for a given text source.
	• Course Advisor - uses the document analysis module to extract meta data from various sources (i.e documents, registration history, user’s interests) and outputs a list of course and instructor recommendations.


You can make use of the afore mentioned modules using the wrappers described in various examples in the "se.kth.ict.docaid.api.examples" package.
The following list contains the wrappers that use the implemented modules.

• CourseIndexBuilderWrp. Uses modules the modules “Course Web Page Parser” and “Course XML parser” to populate the database with course meta data.
• URL/Document parser. Represents the modules “Parse Web Pages” and “Read Documents”. It uses Apache Tika to parse web pages or documents and provide the text output as input to the rest of the modules.
• KeywordExtractorWrp. Uses module “Extract frequently used (key)words” along with Palladian to extract keywords, then uses a keyword filter module which removes from the result stop words and character sequences
that were falsely defined as keywords (e.g., words that contain numbers such as LAB1).
• KeyphraseExtractorWrp. Uses module “Extract keyphrases” along with MAUI to extract keyphrases.
• AcronymExtractorWrp. Uses module “Extract Acronyms” to identify acronyms.
• DocAnalyzerWrp. Document Analyzer is just a wrapper that combines the the previous three wrappers and provides a summary of the results.
• CourseAdvisorWrp. Takes as input a list of text sources (multiple documents) and collects their output from the DocAnalyzerWrp. Optionally takes as input the registration history of a KTH student, extracts the course codes
and provides them along with the keyphrases, keywords, and acronyms as input to the recommender, which finally outputs course and tutor recommendations. The recommender is build upon modules “Suggest courses” and “Suggest tutors”.


To better understand the project we advise you to read the techincal report.
