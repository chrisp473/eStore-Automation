<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <style>
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
	}
	tr:nth-child(odd) {background: #9acd32}
	
  </style>
  <body bgcolor="#ebfafa">
  <h1 style="color:red">Test Automation Service</h1>
  <h2 style="color:red">Execution Run Report</h2>
  <h2 style="color:purple">Summary</h2>
  <table border="0" bordercolor="purple">
  <tr>
  <th>Application</th>
  <th>PASS</th>
  <th>FAIL </th>
  </tr>
  <tr>
  <td>PIBS</td>
  <td bgcolor="#9acd32"> 	
   <xsl:value-of select="count(TestResults/TestScript[@application='PIBS']/Status[text()='PASS'])"/> 
  </td>
  <td bgcolor="red">
<xsl:value-of select="count(TestResults/TestScript[@application='PIBS']/Status[text()='FAIL'])"/> 	
 </td>
</tr>
<tr>
  <td>SMILE</td>
  <td bgcolor="#9acd32"> 	
   <xsl:value-of select="count(TestResults/TestScript[@application='SMILE']/Status[text()='PASS'])"/> 
  </td>
  <td bgcolor ="red" >
<xsl:value-of select="count(TestResults/TestScript[@application='SMILE']/Status[text()='FAIL'])"/> 	
 </td>
</tr>
</table>
<h2 style="color:purple">Details</h2>
   <table border="0" >
      <tr>
		<th bgcolor="yellow">Application Name</th>
		<th bgcolor="yellow">Test Script Name</th>
        <th bgcolor="yellow">Execution Staus</th>
		<th bgcolor="yellow">Execution Time[hh:mm:ss]</th>
      </tr>
      <xsl:for-each select="TestResults/TestScript">
      <tr>
	<td> <xsl:value-of select="@application"/></td>
        <td>
			<xsl:value-of select="@name"/>
			<xsl:text>[</xsl:text>
			<xsl:value-of select="@browser" />
			<xsl:text>,</xsl:text>
			<xsl:value-of select="@OS" />
			<xsl:text>]</xsl:text>
		</td>
       	<xsl:if test="Status = 'PASS'" >
			<td bgcolor="#9acd32"><a href="{ResultFolder}"><xsl:value-of select="Status"/></a></td>
		</xsl:if>
		<xsl:if test="Status != 'PASS'">
			<td bgcolor="red"><a href="{ResultFolder}"><xsl:value-of select="Status"/></a></td>
		</xsl:if>
		<td><xsl:value-of select="ExecutionTime"/></td>
      </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>