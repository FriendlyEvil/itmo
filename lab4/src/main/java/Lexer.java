public class Lexer {
    String template =   "import java.io.InputStream\n" +
                    "    import java.io.IOException\n" +
                    "    import java.text.ParseException\n" +
                    "    public class LexicalException(str: String, pos: Int): ParseException(str, pos)\n" +
                    "    public data class InputToken(val name: String, val value: Regex, val skip: Boolean = false)\n" +
                    "    data class Term(val name: String, val text: String)\n" +
                    "    public class %sLexer(val ins: InputStream) {\n" +
                    "        private var curChar = 0\n" +
                    "        private val states = listOf<InputToken>(\n" +
                    "            %s\n" +
                    "        )\n" +
                    "        private val globalRegex = Regex(\"%s\", RegexOption.DOT_MATCHES_ALL)\n" +
                    "        var curPos = 0\n" +
                    "            private set\n" +
                    "        var curLine = 1\n" +
                    "            private set\n" +
                    "        var curIndex = 0\n" +
                    "            private set\n" +
                    "        lateinit var curToken: Term private set\n" +
                    "        init {\n" +
                    "            nextChar()\n" +
                    "        }\n" +
                    "        private fun nextChar() {\n" +
                    "            curPos++\n" +
                    "            curIndex++\n" +
                    "            try {\n" +
                    "                curChar = ins.read()\n" +
                    "            } catch (e: IOException) {\n" +
                    "                throw ParseException(e.message, curPos)\n" +
                    "            }\n" +
                    "            if (curChar != -1 && curChar.toChar() == '\\n') {\n" +
                    "                curLine++\n" +
                    "                curIndex = 1\n" +
                    "            }\n" +
                    "        }\n" +
                    "        public fun nextToken() {\n" +
                    "            val text = StringBuilder().append(curChar.toChar())\n" +
                    "            if (curChar == -1) {\n" +
                    "                curToken = Term(\"!EOF\", \"${'$'}\")\n" +
                    "                return\n" +
                    "            }\n" +
                    "            val previousLine = curLine\n" +
                    "            val previousIndex = curIndex\n" +
                    "            while (true) {\n" +
                    "                if (text.matches(globalRegex)) {\n" +
                    "                    while (curChar != -1 && text.matches(globalRegex)) {\n" +
                    "                        nextChar()\n" +
                    "                        text.append(curChar.toChar())\n" +
                    "                    }\n" +
                    "                    val str = text.toString().let { it.substring(0, it.lastIndex) }\n" +
                    "                    val matching = states.find { str.matches(it.value) }!!\n" +
                    "                    curToken = Term(matching.name, str)\n" +
                    "                    if (matching.skip) nextToken()\n" +
                    "                    return\n" +
                    "                }\n" +
                    "                nextChar()\n" +
                    "                if (curChar == -1) {\n" +
                    "                    throw LexicalException(\"Unexpected char sequence at ${'$'}previousLine:${'$'}previousIndex\", curPos)\n" +
                    "                }\n" +
                    "                text.append(curChar.toChar())\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }";
}
