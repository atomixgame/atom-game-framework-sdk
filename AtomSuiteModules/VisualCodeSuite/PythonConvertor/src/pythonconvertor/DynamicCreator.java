package pythonconvertor;

import java.io.*;
import java.lang.reflect.*;
import javax.tools.*;
import org.antlr.runtime.*;
import org.antlr.Tool;

/**
 *
 * @author hungcuong
 */
public class DynamicCreator {

    public static void main(String[] args) throws Exception {

        // The grammar which echos the parsed characters to theconsole,
        // skipping any white space chars.
        final String grammar =
                "grammar T;                                                  \n"
                + "                                                            \n"
                + "parse                                                       \n"
                + "  :  (ANY {System.out.println(\"ANY=\" + $ANY.text);})* EOF \n"
                + "  ;                                                         \n"
                + "                                                            \n"
                + "SPACE                                                       \n"
                + "  :  (' ' | '\\t' | '\\r' | '\\n') {skip();}                \n"
                + "  ;                                                         \n"
                + "                                                            \n"
                + "ANY                                                         \n"
                + "  :  .                                                      \n"
                + "  ;                                                           ";
        final String grammarName = "T";
        final String entryPoint = "parse";

        // 1 - Write the `.g` grammar file to disk.
        Writer out = new BufferedWriter(new FileWriter(new File(grammarName + ".g")));
        out.write(grammar);
        out.close();

        // 2 - Generate the lexer and parser.
        Tool tool = new Tool(new String[]{grammarName + ".g"});
        tool.process();

        // 3 - Compile the lexer and parser.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, System.err, "-sourcepath", "", grammarName + "Lexer.java");
        compiler.run(null, System.out, System.err, "-sourcepath", "", grammarName + "Parser.java");

        // 4 - Parse the command line parameter using the dynamically created lexer and 
        //     parser with a bit of reflection Voodoo :)
        Lexer lexer = (Lexer) Class.forName(grammarName + "Lexer").newInstance();
        lexer.setCharStream(new ANTLRStringStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Class<?> parserClass = Class.forName(grammarName + "Parser");
        Constructor parserCTor = parserClass.getConstructor(TokenStream.class);
        Parser parser = (Parser) parserCTor.newInstance(tokens);
        Method entryPointMethod = parserClass.getMethod(entryPoint);

        entryPointMethod.invoke(parser);
    }
}

