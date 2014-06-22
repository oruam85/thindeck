<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" omit-xml-declaration="yes"/>
    <xsl:template match="/">
        <html lang="en-US">
            <head>
                <meta charset="UTF-8"/>
                <meta name="keywords" content="software requirements specification"/>
                <meta name="author" content="requs.org"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <link rel="stylesheet" type="text/css" href="requs.css"/>
                <script src="//code.jquery.com/jquery-2.1.1-rc1.js" type="text/javascript">
                    <!-- nothing -->
                </script>
                <link rel="icon" type="image/png" href="//img.requs.org/logo-128x128.png"/>
                <style type="text/css">* {
    padding: 0;
    margin: 0;
}
body {
    margin: 2em;
    font-family: 'Ubuntu', 'Arial', serif;
    font-size: 16px;
}
a {
    color: #428bca;
    text-decoration: none;
}
a:hover {
    text-decoration: underline;
}
code, pre {
    font-family: 'Monaco', 'Courier New', monospace;
    background-color: #ccc;
}
code {
    padding: 0 .4em;
}
p, ul, ol {
    margin-top: 1em;
    margin-bottom: 1em;
}
td, th {
    padding: .3em;
    border-bottom: 1px solid #ccc;
}
th {
    text-align: left;
    background-color: #ddd;
    border-bottom: 1px solid #444;
    font-weight: 500;
}
h1 {
    margin-top: 2em;
    font-size: 1.4em;
}
h2 {
    margin-top: 2em;
    font-size: 1.2em;
}
li {
    margin-left: 2em;
}
.intro { font-size: 0.9em; color: #999; }
.type { margin-top: 3em; }
.slots { margin-left: 1em; margin-top: 1em; }
.method { margin-top: 1em; margin-left: 1em; }
.steps { margin-top: 1em; margin-left: 1em }
.step { margin-top: 0.25em; }
.nfrs { margin-top: 1em; margin-left: 1em }
.nfr { margin-top: 0.25em; }
.exception { margin-left: 1em; margin-top: 1em; margin-bottom: 1em; }
.informal { color: #666; }
.warning { color: #d9534f; }
.crud, .fail { color: #5cb85c; }
.label { margin-left: 0.5em; color: white; border-radius: .25em; font-size: 0.85em; padding: .1em .3em .15em; }
.attribute { background-color: #999; }
.sealed { background-color: #5cb85c; }
.seal { background-color: #5bc0de; }
.svg { margin-top: 0.5em; margin-bottom: 0.5em; }
</style>
                <title>SRS</title>
            </head>
            <body>
                <div class="container">
                    <xsl:apply-templates select="spec"/>
                </div>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="spec">
        <p class="intro">
            <xsl:text>This document was built in </xsl:text>
            <xsl:value-of select="@msec"/>
            <xsl:text> msec on </xsl:text>
            <xsl:value-of select="@time"/>
            <xsl:text> by </xsl:text>
            <a href="http://www.requs.org">
                <xsl:text>Requs</xsl:text>
            </a>
            <xsl:text> compiler </xsl:text>
            <xsl:value-of select="requs/version"/>
            <xsl:text>/</xsl:text>
            <xsl:value-of select="requs/revision"/>
            <xsl:text>.</xsl:text>
        </p>
        <xsl:if test="errors[error]">
            <h1><xsl:text>Errors</xsl:text></h1>
            <xsl:apply-templates select="errors/error"/>
        </xsl:if>
        <xsl:if test="metrics[metric]">
            <h1><xsl:text>Metrics</xsl:text></h1>
            <xsl:apply-templates select="metrics/metric"/>
        </xsl:if>
        <h1><xsl:text>Types and Methods</xsl:text></h1>
        <xsl:apply-templates select="types/type"/>
        <xsl:if test="pages[page]">
            <h1><xsl:text>Markdown Pages</xsl:text></h1>
            <xsl:apply-templates select="pages/page"/>
        </xsl:if>
        <xsl:if test="tbds[tbd]">
            <xsl:apply-templates select="tbds"/>
        </xsl:if>
    </xsl:template>
    <xsl:template match="metric">
        <p>
            <xsl:value-of select="@id"/>
            <xsl:text> = </xsl:text>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    <xsl:template match="error">
        <p>
            <xsl:text>[</xsl:text>
            <xsl:value-of select="@line"/>
            <xsl:text>:</xsl:text>
            <xsl:value-of select="@pos"/>
            <xsl:text>] </xsl:text>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    <xsl:template match="page">
        <h2><xsl:value-of select="title"/></h2>
        <xsl:value-of select="html" disable-output-escaping="yes"/>
    </xsl:template>
    <xsl:template match="types/type">
        <div class="type">
            <a name="{name}"/>
            <strong><xsl:value-of select="name"/></strong>
            <xsl:text> is </xsl:text>
            <xsl:if test="parents/type">
                <xsl:for-each select="parents/type">
                    <xsl:if test="position() &gt; 1">
                        <xsl:text> and </xsl:text>
                    </xsl:if>
                    <a href="#{.}">
                        <xsl:value-of select="."/>
                    </a>
                </xsl:for-each>
            </xsl:if>
            <xsl:choose>
                <xsl:when test="info/informal">
                    <xsl:apply-templates select="info/informal"/>
                </xsl:when>
                <xsl:when test="not(parents/type)">
                    <span class="warning">
                        <xsl:text>an unknown creature</xsl:text>
                    </span>
                </xsl:when>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="slots/slot">
                    <xsl:text> that includes:</xsl:text>
                    <div class="slots">
                        <ul>
                            <xsl:apply-templates select="slots/slot"/>
                        </ul>
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>.</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates select="/spec/methods/method[bindings/binding[name='_self' and type=current()/name]]"/>
        </div>
    </xsl:template>
    <xsl:template match="slot">
        <li>
            <xsl:value-of select="name"/>
            <xsl:choose>
                <xsl:when test="arity = 'ANY'">
                    <xsl:text>-s?</xsl:text>
                </xsl:when>
                <xsl:when test="arity = 'MANY'">
                    <xsl:text>-s</xsl:text>
                </xsl:when>
                <xsl:when test="arity = 'OPT'">
                    <xsl:text>-?</xsl:text>
                </xsl:when>
            </xsl:choose>
            <xsl:if test="type">
                <xsl:text> as </xsl:text>
                <a href="#{type}">
                    <xsl:value-of select="type"/>
                </a>
            </xsl:if>
            <xsl:apply-templates select="info/informal"/>
        </li>
    </xsl:template>
    <xsl:template match="method">
        <div class="method">
            <a name="{id}"/>
            <div>
                <strong><xsl:value-of select="id"/></strong>
                <xsl:text> where </xsl:text>
                <xsl:call-template name="signature">
                    <xsl:with-param name="bindings" select="bindings"/>
                    <xsl:with-param name="home" select="."/>
                    <xsl:with-param name="typed" select="'true'"/>
                </xsl:call-template>
                <xsl:text>:</xsl:text>
                <xsl:if test="@seal">
                    <span class="label seal">
                        <xsl:value-of select="@seal"/>
                    </span>
                </xsl:if>
                <xsl:apply-templates select="attributes/attribute"/>
            </div>
            <xsl:apply-templates select="svg"/>
            <div class="steps">
                <xsl:apply-templates select="info/informal"/>
                <xsl:apply-templates select="steps"/>
            </div>
            <div class="nfrs">
                <xsl:apply-templates select="nfrs/nfr"/>
            </div>
        </div>
    </xsl:template>
    <xsl:template match="attribute">
        <span>
            <xsl:attribute name="class">
                <xsl:text>label </xsl:text>
                <xsl:choose>
                    <xsl:when test="@seal = ../../@seal">
                        <xsl:text>sealed</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>attribute</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:value-of select="."/>
        </span>
    </xsl:template>
    <xsl:template match="steps">
        <div class="steps">
            <xsl:apply-templates select="step"/>
        </div>
    </xsl:template>
    <xsl:template match="step">
        <div class="step">
            <xsl:value-of select="number"/>
            <xsl:text>. </xsl:text>
            <xsl:call-template name="signature">
                <xsl:with-param name="bindings" select="../../bindings"/>
                <xsl:with-param name="home" select="."/>
            </xsl:call-template>
            <xsl:apply-templates select="info/informal"/>
            <xsl:apply-templates select="exceptions/exception"/>
        </div>
    </xsl:template>
    <xsl:template match="exception">
        <div class="exception">
            <xsl:text>if/when &quot;</xsl:text>
            <xsl:value-of select="when"/>
            <xsl:text>&quot;:</xsl:text>
            <xsl:apply-templates select="steps"/>
        </div>
    </xsl:template>
    <xsl:template match="nfr">
        <div class="nfr">
            <xsl:value-of select="id"/>
            <xsl:text> must </xsl:text>
            <xsl:apply-templates select="info/informal"/>
        </div>
    </xsl:template>
    <xsl:template match="informal">
        <xsl:text> </xsl:text>
        <span class="informal">
            <xsl:text>&quot;</xsl:text>
            <xsl:value-of select="."/>
            <xsl:text>&quot;</xsl:text>
        </span>
        <xsl:text> </xsl:text>
    </xsl:template>
    <xsl:template name="signature">
        <xsl:param name="bindings" />
        <xsl:param name="home" />
        <xsl:param name="typed" select="'false'" />
        <xsl:call-template name="ref">
            <xsl:with-param name="bindings" select="$bindings"/>
            <xsl:with-param name="name" select="$home/object"/>
            <xsl:with-param name="typed" select="$typed"/>
        </xsl:call-template>
        <xsl:text> </xsl:text>
        <xsl:variable name="uc" select="/spec/methods/method[signature=$home/signature]/id"/>
        <xsl:choose>
            <xsl:when test="$home/signature='creates' or $home/signature='reads' or $home/signature='updates' or $home/signature='deletes' or $home/signature='lists'">
                <span class="crud">
                    <xsl:value-of select="$home/signature"/>
                </span>
            </xsl:when>
            <xsl:when test="$home/signature='fail'">
                <span class="fail">
                    <xsl:text>Fail since</xsl:text>
                </span>
            </xsl:when>
            <xsl:when test="$uc">
                <a href="#{$uc}">
                    <xsl:value-of select="$home/signature"/>
                </a>
            </xsl:when>
            <xsl:otherwise>
                <span class="warning">
                    <xsl:value-of select="$home/signature"/>
                </span>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:text> </xsl:text>
        <xsl:if test="$home/result">
            <xsl:call-template name="ref">
                <xsl:with-param name="bindings" select="$bindings"/>
                <xsl:with-param name="name" select="$home/result"/>
                <xsl:with-param name="typed" select="$typed"/>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$home/args/arg">
            <xsl:text> using </xsl:text>
            <xsl:for-each select="$home/args/arg">
                <xsl:if test="position() > 1">
                    <xsl:text> and </xsl:text>
                </xsl:if>
                <xsl:call-template name="ref">
                    <xsl:with-param name="bindings" select="$bindings"/>
                    <xsl:with-param name="name" select="."/>
                    <xsl:with-param name="typed" select="$typed"/>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
    <xsl:template name="ref">
        <xsl:param name="bindings" />
        <xsl:param name="name" />
        <xsl:param name="typed" select="'false'"/>
        <xsl:variable name="type" select="$bindings/binding[name=$name]/type"/>
        <a href="#{$type}">
            <xsl:choose>
                <xsl:when test="contains($name,'_')">
                    <xsl:value-of select="$type"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="$typed = 'true'">
                            <xsl:value-of select="$type"/>
                            <xsl:text> (a </xsl:text>
                            <xsl:value-of select="$name"/>
                            <xsl:text>)</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$name"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </a>
    </xsl:template>
    <xsl:template match="tbds">
        <h1><xsl:text>TBDs</xsl:text></h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Subject</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <xsl:apply-templates select="tbd"/>
            </tbody>
        </table>
    </xsl:template>
    <xsl:template match="tbd">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="subject"/></td>
            <td><xsl:value-of select="description"/></td>
        </tr>
    </xsl:template>
    <xsl:template match="svg">
        <!--
        Doesn't work because of a bug in PlantUML integration,
        see PlantTest.java for more details.
        <div class="uml">
            <xsl:value-of select="." disable-output-escaping="yes"/>
        </div>
        -->
    </xsl:template>
</xsl:stylesheet>