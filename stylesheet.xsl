<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <STYLE type="text/css">
                    html, body {
                    font-family: sans-serif;
                    }
                    .wikitable {
                    background-color: #f8f9fa;
                    color: #222;
                    margin: 1em 0;
                    border: 1px solid #a2a9b1;
                    border-collapse: collapse;
                    }
                    .wikitable > tr > th, .wikitable > * > tr > th {
                    background-color: #eaecf0;
                    text-align: center;
                    }
                    .wikitable > * > tr > th, .wikitable > * > tr > td {
                    border: 1px solid #a2a9b1;
                    padding: 0.2em 0.4em;
                    }
                </STYLE>
            </head>
            <body>
                <table class="wikitable">
                    <tbody>
                        <xsl:if test="solutions/solution/house/@position">
                            <tr>
                                <th>House</th>
                                <xsl:for-each select="solutions/solution/house">
                                    <th>
                                        <xsl:value-of select="@position"/>
                                    </th>
                                </xsl:for-each>
                            </tr>
                        </xsl:if>
                        <xsl:if test="solutions/solution/house/@color">
                            <tr>
                                <th>Color</th>
                                <xsl:for-each select="solutions/solution/house">
                                    <td>
                                        <xsl:value-of select="@color"/>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:if>
                        <xsl:if test="solutions/solution/house/@nationality">
                            <tr>
                                <th>Nationality</th>
                                <xsl:for-each select="solutions/solution/house">
                                    <td>
                                        <xsl:value-of select="@nationality"/>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:if>
                        <xsl:if test="solutions/solution/house/@drink">
                            <tr>
                                <th>Drink</th>
                                <xsl:for-each select="solutions/solution/house">
                                    <td>
                                        <xsl:value-of select="@drink"/>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:if>
                        <xsl:if test="solutions/solution/house/@smoke">
                            <tr>
                                <th>Smoke</th>
                                <xsl:for-each select="solutions/solution/house">
                                    <td>
                                        <xsl:value-of select="@smoke"/>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:if>
                        <xsl:if test="solutions/solution/house/@pet">
                            <tr>
                                <th>Pet</th>
                                <xsl:for-each select="solutions/solution/house">
                                    <td>
                                        <xsl:value-of select="@pet"/>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:if>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>