package Compiler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import parser.Parser;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import lexer.Lexer;
import lexer.LexicalError;
import token.Token;
import exceptions.AnalyzerException;
import token.TokenType;

@SuppressWarnings("serial")
public class GUI extends JPanel {
    private JFrame frame;

    private JPanel codePanel;
    private JPanel buttonPanel;

    private JTabbedPane tabbedPane;

    private JTextArea codeArea;
    private JTextArea lexArea;
    private JTextArea syntaxArea;


    private JButton runBtn;

    private String sourceCode;

    public GUI(JFrame frame) {
        this.frame = frame;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        codePanel = new JPanel();
        codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.Y_AXIS));
        codeArea = new JTextArea(30, 50);
        codeArea.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane codeScrollPane = new JScrollPane(codeArea);
        codeScrollPane.setAutoscrolls(true);
        codePanel.add(codeScrollPane);

        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        runBtn = new JButton("Run");
        runBtn.addActionListener(new RunActionListener());
        buttonPanel.add(runBtn);

        codePanel.add(buttonPanel);
        this.add(codePanel);

        tabbedPane = new JTabbedPane();

        lexArea = new JTextArea(32, 40);
        lexArea.setEditable(false);
        JScrollPane lexScrollPane = new JScrollPane(lexArea);
        lexScrollPane.setAutoscrolls(true);
        tabbedPane.add("Lexical analysis", lexScrollPane);

        syntaxArea = new JTextArea(32, 40);
        syntaxArea.setEditable(false);
        JScrollPane syntaxScrollPane = new JScrollPane(syntaxArea);
        syntaxScrollPane.setAutoscrolls(true);


        this.add(tabbedPane);

    }


    private class RunActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Lexer lexer = new Lexer();

            try {
                lexArea.setText("");
                syntaxArea.setText("");

                // lexer
                sourceCode = codeArea.getText();
                lexer.tokenize(sourceCode);
                JOptionPane.showMessageDialog(frame, "Lexical Analysis is completed",
                        "Information", JOptionPane.INFORMATION_MESSAGE);

            } catch (AnalyzerException exception) {
                JOptionPane.showMessageDialog(frame, exception.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
             finally {
                int i = 0;
                for (Token token : lexer.getTokens()) {
                    if (token.getTokenType().isAuxiliary())
                        lexArea.append("   " + token.toString() + "LINE"+ token.getLineNumber()+"\n" );
                    else {
                        i++;
                        lexArea.append(i + "   " + token.toString() + "LINE"+ token.getLineNumber()+"\n");
                    }
                }
                lexArea.append("Errors:\n");
                for(LexicalError lexicalError: lexer.getError()){
                    lexArea.append(i+" " + lexicalError.getError() + "Line:"+lexicalError.getLineNo());
                }
                List<Token> ts= lexer.getTokens();
                Token temp = new Token(-1,-1,"$", TokenType.EndMarker,-1);
                ts.add(temp);
                List<Token> filtered=new ArrayList<>();
                for(Token item:ts){
                    if(item.getTokenString().equals(" ") || item.getTokenString().equals("\n")){

                    }
                    else{
                        filtered.add(item);
                    }
                }

                Parser parser=new Parser(filtered);
                for(Token token: parser.getToken()){
                    System.out.println("P"+token.getTokenString());
                }

                System.out.println("LAST" + parser.S());
                System.out.println(filtered.toString());
//                System.out.println("PARSER"+parser.dec());
//                System.out.println("OE"+ parser.OE());
                System.out.println("FOR" + parser.For());

            }
        }
    }


}
