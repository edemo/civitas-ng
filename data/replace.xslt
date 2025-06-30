<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:pom="http://maven.apache.org/POM/4.0.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="pom">

<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:template match="pom:excludedClasses" priority="10">
		<pom:excludedClasses>
			<xsl:copy-of select="document('target/excludedClasses.xml')//pom:param"/>
		</pom:excludedClasses>
	</xsl:template>

  <xsl:template match="@*|*|processing-instruction()|comment()">
    <xsl:copy>
      <xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()"/>
    </xsl:copy>
  </xsl:template>


</xsl:stylesheet>


