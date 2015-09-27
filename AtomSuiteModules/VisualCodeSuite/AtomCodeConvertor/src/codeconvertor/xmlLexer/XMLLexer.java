package codeconvertor.xmlLexer;

// $ANTLR null E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g 2014-03-07 23:45:50

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/** XML parser by Oliver Zeigermann October 10, 2005 */
@SuppressWarnings("all")
public class XMLLexer extends Lexer {
	public static final int EOF=-1;
	public static final int ATTRIBUTE=4;
	public static final int CDATA=5;
	public static final int COMMENT=6;
	public static final int DOCTYPE=7;
	public static final int DOCUMENT=8;
	public static final int ELEMENT=9;
	public static final int EMPTY_ELEMENT=10;
	public static final int END_TAG=11;
	public static final int GENERIC_ID=12;
	public static final int INTERNAL_DTD=13;
	public static final int LETTER=14;
	public static final int PCDATA=15;
	public static final int PI=16;
	public static final int START_TAG=17;
	public static final int VALUE=18;
	public static final int WS=19;
	public static final int XMLDECL=20;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public XMLLexer() {} 
	public XMLLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public XMLLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g"; }

	// $ANTLR start "DOCUMENT"
	public final void mDOCUMENT() throws RecognitionException {
		try {
			int _type = DOCUMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:5: ( ( XMLDECL )? ( WS )? ( DOCTYPE )? ( WS )? ELEMENT ( WS )? )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:8: ( XMLDECL )? ( WS )? ( DOCTYPE )? ( WS )? ELEMENT ( WS )?
			{
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:8: ( XMLDECL )?
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0=='<') ) {
				int LA1_1 = input.LA(2);
				if ( (LA1_1=='?') ) {
					alt1=1;
				}
			}
			switch (alt1) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:8: XMLDECL
					{
					mXMLDECL(); 

					}
					break;

			}

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:17: ( WS )?
			int alt2=2;
			switch ( input.LA(1) ) {
				case ' ':
					{
					alt2=1;
					}
					break;
				case '\t':
					{
					alt2=1;
					}
					break;
				case '\n':
					{
					alt2=1;
					}
					break;
				case '\r':
					{
					alt2=1;
					}
					break;
			}
			switch (alt2) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:17: WS
					{
					mWS(); 

					}
					break;

			}

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:21: ( DOCTYPE )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0=='<') ) {
				int LA3_1 = input.LA(2);
				if ( (LA3_1=='!') ) {
					alt3=1;
				}
			}
			switch (alt3) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:21: DOCTYPE
					{
					mDOCTYPE(); 

					}
					break;

			}

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:30: ( WS )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:30: WS
					{
					mWS(); 

					}
					break;

			}

			mELEMENT(); 

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:42: ( WS )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( ((LA5_0 >= '\t' && LA5_0 <= '\n')||LA5_0=='\r'||LA5_0==' ') ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:5:42: WS
					{
					mWS(); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOCUMENT"

	// $ANTLR start "DOCTYPE"
	public final void mDOCTYPE() throws RecognitionException {
		try {
			CommonToken rootElementName=null;
			CommonToken sys1=null;
			CommonToken pub=null;
			CommonToken sys2=null;
			CommonToken dtd=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:9:5: ( '<!DOCTYPE' WS rootElementName= GENERIC_ID WS ( ( 'SYSTEM' WS sys1= VALUE | 'PUBLIC' WS pub= VALUE WS sys2= VALUE ) ( WS )? )? (dtd= INTERNAL_DTD )? '>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:10:9: '<!DOCTYPE' WS rootElementName= GENERIC_ID WS ( ( 'SYSTEM' WS sys1= VALUE | 'PUBLIC' WS pub= VALUE WS sys2= VALUE ) ( WS )? )? (dtd= INTERNAL_DTD )? '>'
			{
			match("<!DOCTYPE"); 

			mWS(); 

			int rootElementNameStart68 = getCharIndex();
			int rootElementNameStartLine68 = getLine();
			int rootElementNameStartCharPos68 = getCharPositionInLine();
			mGENERIC_ID(); 
			rootElementName = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, rootElementNameStart68, getCharIndex()-1);
			rootElementName.setLine(rootElementNameStartLine68);
			rootElementName.setCharPositionInLine(rootElementNameStartCharPos68);

			 System.out.println("ROOTELEMENT: "+rootElementName.getText()); 
			mWS(); 

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:13:9: ( ( 'SYSTEM' WS sys1= VALUE | 'PUBLIC' WS pub= VALUE WS sys2= VALUE ) ( WS )? )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0=='P'||LA8_0=='S') ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:14:13: ( 'SYSTEM' WS sys1= VALUE | 'PUBLIC' WS pub= VALUE WS sys2= VALUE ) ( WS )?
					{
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:14:13: ( 'SYSTEM' WS sys1= VALUE | 'PUBLIC' WS pub= VALUE WS sys2= VALUE )
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0=='S') ) {
						alt6=1;
					}
					else if ( (LA6_0=='P') ) {
						alt6=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 6, 0, input);
						throw nvae;
					}

					switch (alt6) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:14:15: 'SYSTEM' WS sys1= VALUE
							{
							match("SYSTEM"); 

							mWS(); 

							int sys1Start125 = getCharIndex();
							int sys1StartLine125 = getLine();
							int sys1StartCharPos125 = getCharPositionInLine();
							mVALUE(); 
							sys1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sys1Start125, getCharIndex()-1);
							sys1.setLine(sys1StartLine125);
							sys1.setCharPositionInLine(sys1StartCharPos125);

							 System.out.println("SYSTEM: "+sys1.getText()); 
							}
							break;
						case 2 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:17:15: 'PUBLIC' WS pub= VALUE WS sys2= VALUE
							{
							match("PUBLIC"); 

							mWS(); 

							int pubStart185 = getCharIndex();
							int pubStartLine185 = getLine();
							int pubStartCharPos185 = getCharPositionInLine();
							mVALUE(); 
							pub = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, pubStart185, getCharIndex()-1);
							pub.setLine(pubStartLine185);
							pub.setCharPositionInLine(pubStartCharPos185);

							mWS(); 

							int sys2Start191 = getCharIndex();
							int sys2StartLine191 = getLine();
							int sys2StartCharPos191 = getCharPositionInLine();
							mVALUE(); 
							sys2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sys2Start191, getCharIndex()-1);
							sys2.setLine(sys2StartLine191);
							sys2.setCharPositionInLine(sys2StartCharPos191);

							 System.out.println("PUBLIC: "+pub.getText()); 
							 System.out.println("SYSTEM: "+sys2.getText()); 
							}
							break;

					}

					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:21:13: ( WS )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( ((LA7_0 >= '\t' && LA7_0 <= '\n')||LA7_0=='\r'||LA7_0==' ') ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:21:15: WS
							{
							mWS(); 

							}
							break;

					}

					}
					break;

			}

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:23:9: (dtd= INTERNAL_DTD )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0=='[') ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:23:11: dtd= INTERNAL_DTD
					{
					int dtdStart291 = getCharIndex();
					int dtdStartLine291 = getLine();
					int dtdStartCharPos291 = getCharPositionInLine();
					mINTERNAL_DTD(); 
					dtd = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, dtdStart291, getCharIndex()-1);
					dtd.setLine(dtdStartLine291);
					dtd.setCharPositionInLine(dtdStartCharPos291);

					 System.out.println("INTERNAL DTD: "+dtd.getText()); 
					}
					break;

			}

			match('>'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOCTYPE"

	// $ANTLR start "INTERNAL_DTD"
	public final void mINTERNAL_DTD() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:29:23: ( '[' ( options {greedy=false; } : . )* ']' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:29:25: '[' ( options {greedy=false; } : . )* ']'
			{
			match('['); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:29:29: ( options {greedy=false; } : . )*
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( (LA10_0==']') ) {
					alt10=2;
				}
				else if ( ((LA10_0 >= '\u0000' && LA10_0 <= '\\')||(LA10_0 >= '^' && LA10_0 <= '\uFFFF')) ) {
					alt10=1;
				}

				switch (alt10) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:29:56: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop10;
				}
			}

			match(']'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTERNAL_DTD"

	// $ANTLR start "PI"
	public final void mPI() throws RecognitionException {
		try {
			CommonToken target=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:31:13: ( '<?' target= GENERIC_ID ( WS )? ( ATTRIBUTE ( WS )? )* '?>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:32:9: '<?' target= GENERIC_ID ( WS )? ( ATTRIBUTE ( WS )? )* '?>'
			{
			match("<?"); 

			int targetStart374 = getCharIndex();
			int targetStartLine374 = getLine();
			int targetStartCharPos374 = getCharPositionInLine();
			mGENERIC_ID(); 
			target = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, targetStart374, getCharIndex()-1);
			target.setLine(targetStartLine374);
			target.setCharPositionInLine(targetStartCharPos374);

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:32:32: ( WS )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( ((LA11_0 >= '\t' && LA11_0 <= '\n')||LA11_0=='\r'||LA11_0==' ') ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:32:32: WS
					{
					mWS(); 

					}
					break;

			}

			 System.out.println("PI: "+target.getText()); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:34:9: ( ATTRIBUTE ( WS )? )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==':'||(LA13_0 >= 'A' && LA13_0 <= 'Z')||LA13_0=='_'||(LA13_0 >= 'a' && LA13_0 <= 'z')) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:34:11: ATTRIBUTE ( WS )?
					{
					mATTRIBUTE(); 

					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:34:21: ( WS )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( ((LA12_0 >= '\t' && LA12_0 <= '\n')||LA12_0=='\r'||LA12_0==' ') ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:34:21: WS
							{
							mWS(); 

							}
							break;

					}

					}
					break;

				default :
					break loop13;
				}
			}

			match("?>"); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PI"

	// $ANTLR start "XMLDECL"
	public final void mXMLDECL() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:37:18: ( '<?' ( 'x' | 'X' ) ( 'm' | 'M' ) ( 'l' | 'L' ) ( WS )? ( ATTRIBUTE ( WS )? )* '?>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:38:9: '<?' ( 'x' | 'X' ) ( 'm' | 'M' ) ( 'l' | 'L' ) ( WS )? ( ATTRIBUTE ( WS )? )* '?>'
			{
			match("<?"); 

			if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:38:44: ( WS )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( ((LA14_0 >= '\t' && LA14_0 <= '\n')||LA14_0=='\r'||LA14_0==' ') ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:38:44: WS
					{
					mWS(); 

					}
					break;

			}

			 System.out.println("XML declaration"); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:40:9: ( ATTRIBUTE ( WS )? )*
			loop16:
			while (true) {
				int alt16=2;
				int LA16_0 = input.LA(1);
				if ( (LA16_0==':'||(LA16_0 >= 'A' && LA16_0 <= 'Z')||LA16_0=='_'||(LA16_0 >= 'a' && LA16_0 <= 'z')) ) {
					alt16=1;
				}

				switch (alt16) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:40:11: ATTRIBUTE ( WS )?
					{
					mATTRIBUTE(); 

					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:40:21: ( WS )?
					int alt15=2;
					int LA15_0 = input.LA(1);
					if ( ((LA15_0 >= '\t' && LA15_0 <= '\n')||LA15_0=='\r'||LA15_0==' ') ) {
						alt15=1;
					}
					switch (alt15) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:40:21: WS
							{
							mWS(); 

							}
							break;

					}

					}
					break;

				default :
					break loop16;
				}
			}

			match("?>"); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "XMLDECL"

	// $ANTLR start "ELEMENT"
	public final void mELEMENT() throws RecognitionException {
		try {
			CommonToken t=null;
			CommonToken pi=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:45:5: ( ( START_TAG ( ELEMENT |t= PCDATA |t= CDATA |t= COMMENT |pi= PI )* END_TAG | EMPTY_ELEMENT ) )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:45:7: ( START_TAG ( ELEMENT |t= PCDATA |t= CDATA |t= COMMENT |pi= PI )* END_TAG | EMPTY_ELEMENT )
			{
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:45:7: ( START_TAG ( ELEMENT |t= PCDATA |t= CDATA |t= COMMENT |pi= PI )* END_TAG | EMPTY_ELEMENT )
			int alt18=2;
			alt18 = dfa18.predict(input);
			switch (alt18) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:45:9: START_TAG ( ELEMENT |t= PCDATA |t= CDATA |t= COMMENT |pi= PI )* END_TAG
					{
					mSTART_TAG(); 

					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:46:13: ( ELEMENT |t= PCDATA |t= CDATA |t= COMMENT |pi= PI )*
					loop17:
					while (true) {
						int alt17=6;
						int LA17_0 = input.LA(1);
						if ( (LA17_0=='<') ) {
							switch ( input.LA(2) ) {
							case '!':
								{
								int LA17_4 = input.LA(3);
								if ( (LA17_4=='[') ) {
									alt17=3;
								}
								else if ( (LA17_4=='-') ) {
									alt17=4;
								}

								}
								break;
							case '?':
								{
								alt17=5;
								}
								break;
							case '\t':
							case '\n':
							case '\r':
							case ' ':
							case ':':
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
							case 'G':
							case 'H':
							case 'I':
							case 'J':
							case 'K':
							case 'L':
							case 'M':
							case 'N':
							case 'O':
							case 'P':
							case 'Q':
							case 'R':
							case 'S':
							case 'T':
							case 'U':
							case 'V':
							case 'W':
							case 'X':
							case 'Y':
							case 'Z':
							case '_':
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
							case 'g':
							case 'h':
							case 'i':
							case 'j':
							case 'k':
							case 'l':
							case 'm':
							case 'n':
							case 'o':
							case 'p':
							case 'q':
							case 'r':
							case 's':
							case 't':
							case 'u':
							case 'v':
							case 'w':
							case 'x':
							case 'y':
							case 'z':
								{
								alt17=1;
								}
								break;
							}
						}
						else if ( ((LA17_0 >= '\u0000' && LA17_0 <= ';')||(LA17_0 >= '=' && LA17_0 <= '\uFFFF')) ) {
							alt17=2;
						}

						switch (alt17) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:46:14: ELEMENT
							{
							mELEMENT(); 

							}
							break;
						case 2 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:47:15: t= PCDATA
							{
							int tStart538 = getCharIndex();
							int tStartLine538 = getLine();
							int tStartCharPos538 = getCharPositionInLine();
							mPCDATA(); 
							t = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, tStart538, getCharIndex()-1);
							t.setLine(tStartLine538);
							t.setCharPositionInLine(tStartCharPos538);

							 System.out.println("PCDATA: \""+t.getText()+"\""); 
							}
							break;
						case 3 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:49:15: t= CDATA
							{
							int tStart574 = getCharIndex();
							int tStartLine574 = getLine();
							int tStartCharPos574 = getCharPositionInLine();
							mCDATA(); 
							t = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, tStart574, getCharIndex()-1);
							t.setLine(tStartLine574);
							t.setCharPositionInLine(tStartCharPos574);

							 System.out.println("CDATA: \""+t.getText()+"\""); 
							}
							break;
						case 4 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:51:15: t= COMMENT
							{
							int tStart610 = getCharIndex();
							int tStartLine610 = getLine();
							int tStartCharPos610 = getCharPositionInLine();
							mCOMMENT(); 
							t = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, tStart610, getCharIndex()-1);
							t.setLine(tStartLine610);
							t.setCharPositionInLine(tStartCharPos610);

							 System.out.println("Comment: \""+t.getText()+"\""); 
							}
							break;
						case 5 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:53:15: pi= PI
							{
							int piStart646 = getCharIndex();
							int piStartLine646 = getLine();
							int piStartCharPos646 = getCharPositionInLine();
							mPI(); 
							pi = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, piStart646, getCharIndex()-1);
							pi.setLine(piStartLine646);
							pi.setCharPositionInLine(piStartCharPos646);

							}
							break;

						default :
							break loop17;
						}
					}

					mEND_TAG(); 

					}
					break;
				case 2 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:56:11: EMPTY_ELEMENT
					{
					mEMPTY_ELEMENT(); 

					}
					break;

			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ELEMENT"

	// $ANTLR start "START_TAG"
	public final void mSTART_TAG() throws RecognitionException {
		try {
			CommonToken name=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:61:5: ( '<' ( WS )? name= GENERIC_ID ( WS )? ( ATTRIBUTE ( WS )? )* '>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:61:7: '<' ( WS )? name= GENERIC_ID ( WS )? ( ATTRIBUTE ( WS )? )* '>'
			{
			match('<'); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:61:11: ( WS )?
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( ((LA19_0 >= '\t' && LA19_0 <= '\n')||LA19_0=='\r'||LA19_0==' ') ) {
				alt19=1;
			}
			switch (alt19) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:61:11: WS
					{
					mWS(); 

					}
					break;

			}

			int nameStart724 = getCharIndex();
			int nameStartLine724 = getLine();
			int nameStartCharPos724 = getCharPositionInLine();
			mGENERIC_ID(); 
			name = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, nameStart724, getCharIndex()-1);
			name.setLine(nameStartLine724);
			name.setCharPositionInLine(nameStartCharPos724);

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:61:31: ( WS )?
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( ((LA20_0 >= '\t' && LA20_0 <= '\n')||LA20_0=='\r'||LA20_0==' ') ) {
				alt20=1;
			}
			switch (alt20) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:61:31: WS
					{
					mWS(); 

					}
					break;

			}

			 System.out.println("Start Tag: "+name.getText()); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:63:9: ( ATTRIBUTE ( WS )? )*
			loop22:
			while (true) {
				int alt22=2;
				int LA22_0 = input.LA(1);
				if ( (LA22_0==':'||(LA22_0 >= 'A' && LA22_0 <= 'Z')||LA22_0=='_'||(LA22_0 >= 'a' && LA22_0 <= 'z')) ) {
					alt22=1;
				}

				switch (alt22) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:63:11: ATTRIBUTE ( WS )?
					{
					mATTRIBUTE(); 

					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:63:21: ( WS )?
					int alt21=2;
					int LA21_0 = input.LA(1);
					if ( ((LA21_0 >= '\t' && LA21_0 <= '\n')||LA21_0=='\r'||LA21_0==' ') ) {
						alt21=1;
					}
					switch (alt21) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:63:21: WS
							{
							mWS(); 

							}
							break;

					}

					}
					break;

				default :
					break loop22;
				}
			}

			match('>'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "START_TAG"

	// $ANTLR start "EMPTY_ELEMENT"
	public final void mEMPTY_ELEMENT() throws RecognitionException {
		try {
			CommonToken name=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:67:5: ( '<' ( WS )? name= GENERIC_ID ( WS )? ( ATTRIBUTE ( WS )? )* '/>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:67:7: '<' ( WS )? name= GENERIC_ID ( WS )? ( ATTRIBUTE ( WS )? )* '/>'
			{
			match('<'); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:67:11: ( WS )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( ((LA23_0 >= '\t' && LA23_0 <= '\n')||LA23_0=='\r'||LA23_0==' ') ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:67:11: WS
					{
					mWS(); 

					}
					break;

			}

			int nameStart786 = getCharIndex();
			int nameStartLine786 = getLine();
			int nameStartCharPos786 = getCharPositionInLine();
			mGENERIC_ID(); 
			name = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, nameStart786, getCharIndex()-1);
			name.setLine(nameStartLine786);
			name.setCharPositionInLine(nameStartCharPos786);

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:67:31: ( WS )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( ((LA24_0 >= '\t' && LA24_0 <= '\n')||LA24_0=='\r'||LA24_0==' ') ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:67:31: WS
					{
					mWS(); 

					}
					break;

			}

			 System.out.println("Empty Element: "+name.getText()); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:69:9: ( ATTRIBUTE ( WS )? )*
			loop26:
			while (true) {
				int alt26=2;
				int LA26_0 = input.LA(1);
				if ( (LA26_0==':'||(LA26_0 >= 'A' && LA26_0 <= 'Z')||LA26_0=='_'||(LA26_0 >= 'a' && LA26_0 <= 'z')) ) {
					alt26=1;
				}

				switch (alt26) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:69:11: ATTRIBUTE ( WS )?
					{
					mATTRIBUTE(); 

					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:69:21: ( WS )?
					int alt25=2;
					int LA25_0 = input.LA(1);
					if ( ((LA25_0 >= '\t' && LA25_0 <= '\n')||LA25_0=='\r'||LA25_0==' ') ) {
						alt25=1;
					}
					switch (alt25) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:69:21: WS
							{
							mWS(); 

							}
							break;

					}

					}
					break;

				default :
					break loop26;
				}
			}

			match("/>"); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EMPTY_ELEMENT"

	// $ANTLR start "ATTRIBUTE"
	public final void mATTRIBUTE() throws RecognitionException {
		try {
			CommonToken name=null;
			CommonToken value=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:73:5: (name= GENERIC_ID ( WS )? '=' ( WS )? value= VALUE )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:73:7: name= GENERIC_ID ( WS )? '=' ( WS )? value= VALUE
			{
			int nameStart843 = getCharIndex();
			int nameStartLine843 = getLine();
			int nameStartCharPos843 = getCharPositionInLine();
			mGENERIC_ID(); 
			name = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, nameStart843, getCharIndex()-1);
			name.setLine(nameStartLine843);
			name.setCharPositionInLine(nameStartCharPos843);

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:73:23: ( WS )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( ((LA27_0 >= '\t' && LA27_0 <= '\n')||LA27_0=='\r'||LA27_0==' ') ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:73:23: WS
					{
					mWS(); 

					}
					break;

			}

			match('='); 
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:73:31: ( WS )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( ((LA28_0 >= '\t' && LA28_0 <= '\n')||LA28_0=='\r'||LA28_0==' ') ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:73:31: WS
					{
					mWS(); 

					}
					break;

			}

			int valueStart855 = getCharIndex();
			int valueStartLine855 = getLine();
			int valueStartCharPos855 = getCharPositionInLine();
			mVALUE(); 
			value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart855, getCharIndex()-1);
			value.setLine(valueStartLine855);
			value.setCharPositionInLine(valueStartCharPos855);

			 System.out.println("Attr: "+name.getText()+"="+value.getText()); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ATTRIBUTE"

	// $ANTLR start "END_TAG"
	public final void mEND_TAG() throws RecognitionException {
		try {
			CommonToken name=null;

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:78:5: ( '</' ( WS )? name= GENERIC_ID ( WS )? '>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:78:7: '</' ( WS )? name= GENERIC_ID ( WS )? '>'
			{
			match("</"); 

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:78:12: ( WS )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( ((LA29_0 >= '\t' && LA29_0 <= '\n')||LA29_0=='\r'||LA29_0==' ') ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:78:12: WS
					{
					mWS(); 

					}
					break;

			}

			int nameStart892 = getCharIndex();
			int nameStartLine892 = getLine();
			int nameStartCharPos892 = getCharPositionInLine();
			mGENERIC_ID(); 
			name = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, nameStart892, getCharIndex()-1);
			name.setLine(nameStartLine892);
			name.setCharPositionInLine(nameStartCharPos892);

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:78:32: ( WS )?
			int alt30=2;
			int LA30_0 = input.LA(1);
			if ( ((LA30_0 >= '\t' && LA30_0 <= '\n')||LA30_0=='\r'||LA30_0==' ') ) {
				alt30=1;
			}
			switch (alt30) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:78:32: WS
					{
					mWS(); 

					}
					break;

			}

			match('>'); 
			 System.out.println("End Tag: "+name.getText()); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "END_TAG"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:83:2: ( '<!--' ( options {greedy=false; } : . )* '-->' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:83:4: '<!--' ( options {greedy=false; } : . )* '-->'
			{
			match("<!--"); 

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:83:11: ( options {greedy=false; } : . )*
			loop31:
			while (true) {
				int alt31=2;
				int LA31_0 = input.LA(1);
				if ( (LA31_0=='-') ) {
					int LA31_1 = input.LA(2);
					if ( (LA31_1=='-') ) {
						int LA31_3 = input.LA(3);
						if ( (LA31_3=='>') ) {
							alt31=2;
						}
						else if ( ((LA31_3 >= '\u0000' && LA31_3 <= '=')||(LA31_3 >= '?' && LA31_3 <= '\uFFFF')) ) {
							alt31=1;
						}

					}
					else if ( ((LA31_1 >= '\u0000' && LA31_1 <= ',')||(LA31_1 >= '.' && LA31_1 <= '\uFFFF')) ) {
						alt31=1;
					}

				}
				else if ( ((LA31_0 >= '\u0000' && LA31_0 <= ',')||(LA31_0 >= '.' && LA31_0 <= '\uFFFF')) ) {
					alt31=1;
				}

				switch (alt31) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:83:38: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop31;
				}
			}

			match("-->"); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "CDATA"
	public final void mCDATA() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:87:2: ( '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:87:4: '<![CDATA[' ( options {greedy=false; } : . )* ']]>'
			{
			match("<![CDATA["); 

			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:87:16: ( options {greedy=false; } : . )*
			loop32:
			while (true) {
				int alt32=2;
				int LA32_0 = input.LA(1);
				if ( (LA32_0==']') ) {
					int LA32_1 = input.LA(2);
					if ( (LA32_1==']') ) {
						int LA32_3 = input.LA(3);
						if ( (LA32_3=='>') ) {
							alt32=2;
						}
						else if ( ((LA32_3 >= '\u0000' && LA32_3 <= '=')||(LA32_3 >= '?' && LA32_3 <= '\uFFFF')) ) {
							alt32=1;
						}

					}
					else if ( ((LA32_1 >= '\u0000' && LA32_1 <= '\\')||(LA32_1 >= '^' && LA32_1 <= '\uFFFF')) ) {
						alt32=1;
					}

				}
				else if ( ((LA32_0 >= '\u0000' && LA32_0 <= '\\')||(LA32_0 >= '^' && LA32_0 <= '\uFFFF')) ) {
					alt32=1;
				}

				switch (alt32) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:87:43: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop32;
				}
			}

			match("]]>"); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CDATA"

	// $ANTLR start "PCDATA"
	public final void mPCDATA() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:90:17: ( (~ '<' )+ )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:90:19: (~ '<' )+
			{
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:90:19: (~ '<' )+
			int cnt33=0;
			loop33:
			while (true) {
				int alt33=2;
				int LA33_0 = input.LA(1);
				if ( ((LA33_0 >= '\u0000' && LA33_0 <= ';')||(LA33_0 >= '=' && LA33_0 <= '\uFFFF')) ) {
					alt33=1;
				}

				switch (alt33) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= ';')||(input.LA(1) >= '=' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt33 >= 1 ) break loop33;
					EarlyExitException eee = new EarlyExitException(33, input);
					throw eee;
				}
				cnt33++;
			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PCDATA"

	// $ANTLR start "VALUE"
	public final void mVALUE() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:92:16: ( ( '\\\"' (~ '\\\"' )* '\\\"' | '\\'' (~ '\\'' )* '\\'' ) )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:93:9: ( '\\\"' (~ '\\\"' )* '\\\"' | '\\'' (~ '\\'' )* '\\'' )
			{
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:93:9: ( '\\\"' (~ '\\\"' )* '\\\"' | '\\'' (~ '\\'' )* '\\'' )
			int alt36=2;
			int LA36_0 = input.LA(1);
			if ( (LA36_0=='\"') ) {
				alt36=1;
			}
			else if ( (LA36_0=='\'') ) {
				alt36=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 36, 0, input);
				throw nvae;
			}

			switch (alt36) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:93:11: '\\\"' (~ '\\\"' )* '\\\"'
					{
					match('\"'); 
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:93:16: (~ '\\\"' )*
					loop34:
					while (true) {
						int alt34=2;
						int LA34_0 = input.LA(1);
						if ( ((LA34_0 >= '\u0000' && LA34_0 <= '!')||(LA34_0 >= '#' && LA34_0 <= '\uFFFF')) ) {
							alt34=1;
						}

						switch (alt34) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop34;
						}
					}

					match('\"'); 
					}
					break;
				case 2 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:94:11: '\\'' (~ '\\'' )* '\\''
					{
					match('\''); 
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:94:16: (~ '\\'' )*
					loop35:
					while (true) {
						int alt35=2;
						int LA35_0 = input.LA(1);
						if ( ((LA35_0 >= '\u0000' && LA35_0 <= '&')||(LA35_0 >= '(' && LA35_0 <= '\uFFFF')) ) {
							alt35=1;
						}

						switch (alt35) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop35;
						}
					}

					match('\''); 
					}
					break;

			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VALUE"

	// $ANTLR start "GENERIC_ID"
	public final void mGENERIC_ID() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:99:5: ( ( LETTER | '_' | ':' ) ( options {greedy=true; } : LETTER | '0' .. '9' | '.' | '-' | '_' | ':' )* )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:99:7: ( LETTER | '_' | ':' ) ( options {greedy=true; } : LETTER | '0' .. '9' | '.' | '-' | '_' | ':' )*
			{
			if ( input.LA(1)==':'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:9: ( options {greedy=true; } : LETTER | '0' .. '9' | '.' | '-' | '_' | ':' )*
			loop37:
			while (true) {
				int alt37=7;
				switch ( input.LA(1) ) {
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
				case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
				case 'x':
				case 'y':
				case 'z':
					{
					alt37=1;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					{
					alt37=2;
					}
					break;
				case '.':
					{
					alt37=3;
					}
					break;
				case '-':
					{
					alt37=4;
					}
					break;
				case '_':
					{
					alt37=5;
					}
					break;
				case ':':
					{
					alt37=6;
					}
					break;
				}
				switch (alt37) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:36: LETTER
					{
					mLETTER(); 

					}
					break;
				case 2 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:45: '0' .. '9'
					{
					matchRange('0','9'); 
					}
					break;
				case 3 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:56: '.'
					{
					match('.'); 
					}
					break;
				case 4 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:62: '-'
					{
					match('-'); 
					}
					break;
				case 5 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:68: '_'
					{
					match('_'); 
					}
					break;
				case 6 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:100:74: ':'
					{
					match(':'); 
					}
					break;

				default :
					break loop37;
				}
			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "GENERIC_ID"

	// $ANTLR start "LETTER"
	public final void mLETTER() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:104:2: ( 'a' .. 'z' | 'A' .. 'Z' )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LETTER"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:108:14: ( ( ' ' | '\\t' | ( '\\n' | '\\r\\n' | '\\r' ) )+ )
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:109:9: ( ' ' | '\\t' | ( '\\n' | '\\r\\n' | '\\r' ) )+
			{
			// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:109:9: ( ' ' | '\\t' | ( '\\n' | '\\r\\n' | '\\r' ) )+
			int cnt39=0;
			loop39:
			while (true) {
				int alt39=4;
				switch ( input.LA(1) ) {
				case ' ':
					{
					alt39=1;
					}
					break;
				case '\t':
					{
					alt39=2;
					}
					break;
				case '\n':
				case '\r':
					{
					alt39=3;
					}
					break;
				}
				switch (alt39) {
				case 1 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:109:13: ' '
					{
					match(' '); 
					}
					break;
				case 2 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:110:13: '\\t'
					{
					match('\t'); 
					}
					break;
				case 3 :
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:111:12: ( '\\n' | '\\r\\n' | '\\r' )
					{
					// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:111:12: ( '\\n' | '\\r\\n' | '\\r' )
					int alt38=3;
					int LA38_0 = input.LA(1);
					if ( (LA38_0=='\n') ) {
						alt38=1;
					}
					else if ( (LA38_0=='\r') ) {
						int LA38_2 = input.LA(2);
						if ( (LA38_2=='\n') ) {
							alt38=2;
						}

						else {
							alt38=3;
						}

					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 38, 0, input);
						throw nvae;
					}

					switch (alt38) {
						case 1 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:111:14: '\\n'
							{
							match('\n'); 
							}
							break;
						case 2 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:112:15: '\\r\\n'
							{
							match("\r\n"); 

							}
							break;
						case 3 :
							// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:113:15: '\\r'
							{
							match('\r'); 
							}
							break;

					}

					}
					break;

				default :
					if ( cnt39 >= 1 ) break loop39;
					EarlyExitException eee = new EarlyExitException(39, input);
					throw eee;
				}
				cnt39++;
			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:1:8: ( DOCUMENT )
		// E:\\JGE\\JMEMyModule\\AtomSuiteModules\\VisualCodeSuite\\AtomCodeConvertor\\src\\codeconvertor\\xmlLexer\\XMLLexer.g:1:10: DOCUMENT
		{
		mDOCUMENT(); 

		}

	}


	protected DFA18 dfa18 = new DFA18(this);
	static final String DFA18_eotS =
		"\75\uffff";
	static final String DFA18_eofS =
		"\75\uffff";
	static final String DFA18_minS =
		"\1\74\21\11\2\uffff\22\11\2\0\13\11\1\0\1\11\1\0\7\11";
	static final String DFA18_maxS =
		"\1\74\21\172\2\uffff\12\172\1\47\3\172\4\47\2\uffff\6\172\4\75\1\47\1"+
		"\uffff\1\172\1\uffff\1\172\1\75\5\172";
	static final String DFA18_acceptS =
		"\22\uffff\1\1\1\2\51\uffff";
	static final String DFA18_specialS =
		"\46\uffff\1\0\1\2\13\uffff\1\3\1\uffff\1\1\7\uffff}>";
	static final String[] DFA18_transitionS = {
			"\1\1",
			"\1\3\1\4\2\uffff\1\5\22\uffff\1\2\31\uffff\1\6\6\uffff\32\6\4\uffff"+
			"\1\6\1\uffff\32\6",
			"\1\3\1\4\2\uffff\1\5\22\uffff\1\2\31\uffff\1\6\6\uffff\32\6\4\uffff"+
			"\1\6\1\uffff\32\6",
			"\1\3\1\4\2\uffff\1\5\22\uffff\1\2\31\uffff\1\6\6\uffff\32\6\4\uffff"+
			"\1\6\1\uffff\32\6",
			"\1\3\1\4\2\uffff\1\5\22\uffff\1\2\31\uffff\1\6\6\uffff\32\6\4\uffff"+
			"\1\6\1\uffff\32\6",
			"\1\3\1\7\2\uffff\1\5\22\uffff\1\2\31\uffff\1\6\6\uffff\32\6\4\uffff"+
			"\1\6\1\uffff\32\6",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\14\uffff\1\13\1\12\1\23\12\11"+
			"\1\15\3\uffff\1\22\2\uffff\32\10\4\uffff\1\14\1\uffff\32\10",
			"\1\3\1\4\2\uffff\1\5\22\uffff\1\2\31\uffff\1\6\6\uffff\32\6\4\uffff"+
			"\1\6\1\uffff\32\6",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\14\uffff\1\13\1\12\1\23\12\11"+
			"\1\15\3\uffff\1\22\2\uffff\32\10\4\uffff\1\14\1\uffff\32\10",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\14\uffff\1\13\1\12\1\23\12\11"+
			"\1\15\3\uffff\1\22\2\uffff\32\10\4\uffff\1\14\1\uffff\32\10",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\14\uffff\1\13\1\12\1\23\12\11"+
			"\1\15\3\uffff\1\22\2\uffff\32\10\4\uffff\1\14\1\uffff\32\10",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\17\1\40\2\uffff\1\21\22\uffff\1\16\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"",
			"",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\16\uffff\1\23\12\uffff\1\37\2"+
			"\uffff\1\36\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\16\uffff\1\23\12\uffff\1\37\2"+
			"\uffff\1\36\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\16\uffff\1\23\12\uffff\1\37\2"+
			"\uffff\1\36\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\25\1\41\2\uffff\1\27\22\uffff\1\24\16\uffff\1\23\12\uffff\1\37\2"+
			"\uffff\1\36\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\14\uffff\1\34\1\33\1\23\12\32"+
			"\1\35\2\uffff\1\36\1\22\2\uffff\32\30\4\uffff\1\31\1\uffff\32\30",
			"\1\43\1\44\2\uffff\1\45\22\uffff\1\42\1\uffff\1\46\4\uffff\1\47",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\17\1\20\2\uffff\1\21\22\uffff\1\16\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\25\1\26\2\uffff\1\27\22\uffff\1\24\16\uffff\1\23\12\uffff\1\37\2"+
			"\uffff\1\36\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\43\1\44\2\uffff\1\45\22\uffff\1\42\1\uffff\1\46\4\uffff\1\47",
			"\1\43\1\44\2\uffff\1\45\22\uffff\1\42\1\uffff\1\46\4\uffff\1\47",
			"\1\43\1\44\2\uffff\1\45\22\uffff\1\42\1\uffff\1\46\4\uffff\1\47",
			"\1\43\1\62\2\uffff\1\45\22\uffff\1\42\1\uffff\1\46\4\uffff\1\47",
			"\42\63\1\64\uffdd\63",
			"\47\65\1\66\uffd8\65",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\14\uffff\1\53\1\52\1\uffff\12"+
			"\51\1\55\2\uffff\1\36\3\uffff\32\50\4\uffff\1\54\1\uffff\32\50",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\34\uffff\1\36",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\34\uffff\1\36",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\34\uffff\1\36",
			"\1\57\1\67\2\uffff\1\61\22\uffff\1\56\34\uffff\1\36",
			"\1\43\1\44\2\uffff\1\45\22\uffff\1\42\1\uffff\1\46\4\uffff\1\47",
			"\42\63\1\64\uffdd\63",
			"\1\71\1\72\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\47\65\1\66\uffd8\65",
			"\1\71\1\72\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\57\1\60\2\uffff\1\61\22\uffff\1\56\34\uffff\1\36",
			"\1\71\1\72\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\71\1\72\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\71\1\72\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\71\1\74\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
			"\1\71\1\72\2\uffff\1\73\22\uffff\1\70\16\uffff\1\23\12\uffff\1\37\3"+
			"\uffff\1\22\2\uffff\32\37\4\uffff\1\37\1\uffff\32\37"
	};

	static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
	static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
	static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
	static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
	static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
	static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
	static final short[][] DFA18_transition;

	static {
		int numStates = DFA18_transitionS.length;
		DFA18_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
		}
	}

	protected class DFA18 extends DFA {

		public DFA18(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 18;
			this.eot = DFA18_eot;
			this.eof = DFA18_eof;
			this.min = DFA18_min;
			this.max = DFA18_max;
			this.accept = DFA18_accept;
			this.special = DFA18_special;
			this.transition = DFA18_transition;
		}
		@Override
		public String getDescription() {
			return "45:7: ( START_TAG ( ELEMENT |t= PCDATA |t= CDATA |t= COMMENT |pi= PI )* END_TAG | EMPTY_ELEMENT )";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			IntStream input = _input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA18_38 = input.LA(1);
						s = -1;
						if ( ((LA18_38 >= '\u0000' && LA18_38 <= '!')||(LA18_38 >= '#' && LA18_38 <= '\uFFFF')) ) {s = 51;}
						else if ( (LA18_38=='\"') ) {s = 52;}
						if ( s>=0 ) return s;
						break;

					case 1 : 
						int LA18_53 = input.LA(1);
						s = -1;
						if ( (LA18_53=='\'') ) {s = 54;}
						else if ( ((LA18_53 >= '\u0000' && LA18_53 <= '&')||(LA18_53 >= '(' && LA18_53 <= '\uFFFF')) ) {s = 53;}
						if ( s>=0 ) return s;
						break;

					case 2 : 
						int LA18_39 = input.LA(1);
						s = -1;
						if ( ((LA18_39 >= '\u0000' && LA18_39 <= '&')||(LA18_39 >= '(' && LA18_39 <= '\uFFFF')) ) {s = 53;}
						else if ( (LA18_39=='\'') ) {s = 54;}
						if ( s>=0 ) return s;
						break;

					case 3 : 
						int LA18_51 = input.LA(1);
						s = -1;
						if ( (LA18_51=='\"') ) {s = 52;}
						else if ( ((LA18_51 >= '\u0000' && LA18_51 <= '!')||(LA18_51 >= '#' && LA18_51 <= '\uFFFF')) ) {s = 51;}
						if ( s>=0 ) return s;
						break;
			}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 18, _s, input);
			error(nvae);
			throw nvae;
		}
	}

}
