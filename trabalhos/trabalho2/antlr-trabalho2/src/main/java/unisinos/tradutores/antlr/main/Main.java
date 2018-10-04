package unisinos.tradutores.antlr.main;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import unisinos.tradutores.antlr.main.analiser.MethodAnaliser;
import unisinos.tradutores.antlr.main.gramatica.JavaLexer;
import unisinos.tradutores.antlr.main.gramatica.JavaParser;
import unisinos.tradutores.antlr.main.listener.JavaBaseListenerImpl;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {


    public static void main(String[] args) throws IOException {
        String inputFile = "./src/main/java/unisinos/tradutores/antlr/main/TesteClass.java";

        CharStream cs = CharStreams.fromPath(Paths.get(inputFile));

        JavaLexer lexer = new JavaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit(); // parse

        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        JavaBaseListenerImpl extractor = new JavaBaseListenerImpl();
        walker.walk(extractor, tree); // initiate walk of tree with listener

        MethodAnaliser analiser = MethodAnaliser.builder()
                .methods(extractor.result())
                .build();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        analiser.analise();
    }


}