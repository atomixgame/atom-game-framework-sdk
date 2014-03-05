package pythonconvertor.python.grammar;

// $ANTLR null D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g 2013-10-25 11:54:42

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/** Python 2.3.3 Grammar
 *
 *  Terence Parr and Loring Craymer
 *  February 2004
 *
 *  Converted to ANTLR v3 November 2005 by Terence Parr.
 *
 *  This grammar was derived automatically from the Python 2.3.3
 *  parser grammar to get a syntactically correct ANTLR grammar
 *  for Python.  Then Terence hand tweaked it to be semantically
 *  correct; i.e., removed lookahead issues etc...  It is LL(1)
 *  except for the (sometimes optional) trailing commas and semi-colons.
 *  It needs two symbols of lookahead in this case.
 *
 *  Starting with Loring's preliminary lexer for Python, I modified it
 *  to do my version of the whole nasty INDENT/DEDENT issue just so I
 *  could understand the problem better.  This grammar requires
 *  PythonTokenStream.java to work.  Also I used some rules from the
 *  semi-formal grammar on the web for Python (automatically
 *  translated to ANTLR format by an ANTLR grammar, naturally <grin>).
 *  The lexical rules for python are particularly nasty and it took me
 *  a long time to get it 'right'; i.e., think about it in the proper
 *  way.  Resist changing the lexer unless you've used ANTLR a lot. ;)
 *
 *  I (Terence) tested this by running it on the jython-2.1/Lib
 *  directory of 40k lines of Python.
 *
 *  REQUIRES ANTLR v3
 */
@SuppressWarnings("all")
public class PythonParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALT_NOTEQUAL", "AMPER", "AMPEREQUAL", 
		"ASSIGN", "BACKQUOTE", "CIRCUMFLEX", "CIRCUMFLEXEQUAL", "COLON", "COMMA", 
		"COMMENT", "COMPLEX", "CONTINUED_LINE", "DEDENT", "DIGITS", "DOT", "DOUBLESLASH", 
		"DOUBLESLASHEQUAL", "DOUBLESTAR", "DOUBLESTAREQUAL", "EQUAL", "ESC", "Exponent", 
		"FLOAT", "GREATER", "GREATEREQUAL", "INDENT", "INT", "LBRACK", "LCURLY", 
		"LEADING_WS", "LEFTSHIFT", "LEFTSHIFTEQUAL", "LESS", "LESSEQUAL", "LONGINT", 
		"LPAREN", "MINUS", "MINUSEQUAL", "NAME", "NEWLINE", "NOTEQUAL", "PERCENT", 
		"PERCENTEQUAL", "PLUS", "PLUSEQUAL", "RBRACK", "RCURLY", "RIGHTSHIFT", 
		"RIGHTSHIFTEQUAL", "RPAREN", "SEMI", "SLASH", "SLASHEQUAL", "STAR", "STAREQUAL", 
		"STRING", "TILDE", "VBAR", "VBAREQUAL", "WS", "'and'", "'assert'", "'break'", 
		"'class'", "'continue'", "'def'", "'del'", "'elif'", "'else'", "'except'", 
		"'exec'", "'finally'", "'for'", "'from'", "'global'", "'if'", "'import'", 
		"'in'", "'is'", "'lambda'", "'not'", "'or'", "'pass'", "'print'", "'raise'", 
		"'return'", "'try'", "'while'", "'yield'"
	};
	public static final int EOF=-1;
	public static final int T__64=64;
	public static final int T__65=65;
	public static final int T__66=66;
	public static final int T__67=67;
	public static final int T__68=68;
	public static final int T__69=69;
	public static final int T__70=70;
	public static final int T__71=71;
	public static final int T__72=72;
	public static final int T__73=73;
	public static final int T__74=74;
	public static final int T__75=75;
	public static final int T__76=76;
	public static final int T__77=77;
	public static final int T__78=78;
	public static final int T__79=79;
	public static final int T__80=80;
	public static final int T__81=81;
	public static final int T__82=82;
	public static final int T__83=83;
	public static final int T__84=84;
	public static final int T__85=85;
	public static final int T__86=86;
	public static final int T__87=87;
	public static final int T__88=88;
	public static final int T__89=89;
	public static final int T__90=90;
	public static final int T__91=91;
	public static final int T__92=92;
	public static final int ALT_NOTEQUAL=4;
	public static final int AMPER=5;
	public static final int AMPEREQUAL=6;
	public static final int ASSIGN=7;
	public static final int BACKQUOTE=8;
	public static final int CIRCUMFLEX=9;
	public static final int CIRCUMFLEXEQUAL=10;
	public static final int COLON=11;
	public static final int COMMA=12;
	public static final int COMMENT=13;
	public static final int COMPLEX=14;
	public static final int CONTINUED_LINE=15;
	public static final int DEDENT=16;
	public static final int DIGITS=17;
	public static final int DOT=18;
	public static final int DOUBLESLASH=19;
	public static final int DOUBLESLASHEQUAL=20;
	public static final int DOUBLESTAR=21;
	public static final int DOUBLESTAREQUAL=22;
	public static final int EQUAL=23;
	public static final int ESC=24;
	public static final int Exponent=25;
	public static final int FLOAT=26;
	public static final int GREATER=27;
	public static final int GREATEREQUAL=28;
	public static final int INDENT=29;
	public static final int INT=30;
	public static final int LBRACK=31;
	public static final int LCURLY=32;
	public static final int LEADING_WS=33;
	public static final int LEFTSHIFT=34;
	public static final int LEFTSHIFTEQUAL=35;
	public static final int LESS=36;
	public static final int LESSEQUAL=37;
	public static final int LONGINT=38;
	public static final int LPAREN=39;
	public static final int MINUS=40;
	public static final int MINUSEQUAL=41;
	public static final int NAME=42;
	public static final int NEWLINE=43;
	public static final int NOTEQUAL=44;
	public static final int PERCENT=45;
	public static final int PERCENTEQUAL=46;
	public static final int PLUS=47;
	public static final int PLUSEQUAL=48;
	public static final int RBRACK=49;
	public static final int RCURLY=50;
	public static final int RIGHTSHIFT=51;
	public static final int RIGHTSHIFTEQUAL=52;
	public static final int RPAREN=53;
	public static final int SEMI=54;
	public static final int SLASH=55;
	public static final int SLASHEQUAL=56;
	public static final int STAR=57;
	public static final int STAREQUAL=58;
	public static final int STRING=59;
	public static final int TILDE=60;
	public static final int VBAR=61;
	public static final int VBAREQUAL=62;
	public static final int WS=63;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public PythonParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public PythonParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return PythonParser.tokenNames; }
	@Override public String getGrammarFileName() { return "D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g"; }



	// $ANTLR start "single_input"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:75:1: single_input : ( NEWLINE | simple_stmt | compound_stmt NEWLINE );
	public final void single_input() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:76:5: ( NEWLINE | simple_stmt | compound_stmt NEWLINE )
			int alt1=3;
			switch ( input.LA(1) ) {
			case NEWLINE:
				{
				alt1=1;
				}
				break;
			case BACKQUOTE:
			case COMPLEX:
			case FLOAT:
			case INT:
			case LBRACK:
			case LCURLY:
			case LONGINT:
			case LPAREN:
			case MINUS:
			case NAME:
			case PLUS:
			case STRING:
			case TILDE:
			case 65:
			case 66:
			case 68:
			case 70:
			case 74:
			case 77:
			case 78:
			case 80:
			case 83:
			case 84:
			case 86:
			case 87:
			case 88:
			case 89:
			case 92:
				{
				alt1=2;
				}
				break;
			case 67:
			case 69:
			case 76:
			case 79:
			case 90:
			case 91:
				{
				alt1=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:76:7: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_single_input47); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:77:4: simple_stmt
					{
					pushFollow(FOLLOW_simple_stmt_in_single_input52);
					simple_stmt();
					state._fsp--;

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:78:4: compound_stmt NEWLINE
					{
					pushFollow(FOLLOW_compound_stmt_in_single_input57);
					compound_stmt();
					state._fsp--;

					match(input,NEWLINE,FOLLOW_NEWLINE_in_single_input59); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "single_input"



	// $ANTLR start "file_input"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:81:1: file_input : ( NEWLINE | stmt )* ;
	public final void file_input() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:82:5: ( ( NEWLINE | stmt )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:82:9: ( NEWLINE | stmt )*
			{
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:82:9: ( NEWLINE | stmt )*
			loop2:
			while (true) {
				int alt2=3;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==NEWLINE) ) {
					alt2=1;
				}
				else if ( (LA2_0==BACKQUOTE||LA2_0==COMPLEX||LA2_0==FLOAT||(LA2_0 >= INT && LA2_0 <= LCURLY)||(LA2_0 >= LONGINT && LA2_0 <= MINUS)||LA2_0==NAME||LA2_0==PLUS||(LA2_0 >= STRING && LA2_0 <= TILDE)||(LA2_0 >= 65 && LA2_0 <= 70)||LA2_0==74||(LA2_0 >= 76 && LA2_0 <= 80)||(LA2_0 >= 83 && LA2_0 <= 84)||(LA2_0 >= 86 && LA2_0 <= 92)) ) {
					alt2=2;
				}

				switch (alt2) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:82:10: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_file_input76); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:82:20: stmt
					{
					pushFollow(FOLLOW_stmt_in_file_input80);
					stmt();
					state._fsp--;

					}
					break;

				default :
					break loop2;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "file_input"



	// $ANTLR start "eval_input"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:85:1: eval_input : ( NEWLINE )* testlist ( NEWLINE )* ;
	public final void eval_input() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:86:5: ( ( NEWLINE )* testlist ( NEWLINE )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:86:9: ( NEWLINE )* testlist ( NEWLINE )*
			{
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:86:9: ( NEWLINE )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==NEWLINE) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:86:10: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_eval_input99); 
					}
					break;

				default :
					break loop3;
				}
			}

			pushFollow(FOLLOW_testlist_in_eval_input103);
			testlist();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:86:29: ( NEWLINE )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==NEWLINE) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:86:30: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_eval_input106); 
					}
					break;

				default :
					break loop4;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "eval_input"



	// $ANTLR start "funcdef"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:89:1: funcdef : 'def' NAME parameters COLON suite ;
	public final void funcdef() throws RecognitionException {
		Token NAME1=null;

		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:90:5: ( 'def' NAME parameters COLON suite )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:90:9: 'def' NAME parameters COLON suite
			{
			match(input,69,FOLLOW_69_in_funcdef124); 
			NAME1=(Token)match(input,NAME,FOLLOW_NAME_in_funcdef126); 
			pushFollow(FOLLOW_parameters_in_funcdef128);
			parameters();
			state._fsp--;

			match(input,COLON,FOLLOW_COLON_in_funcdef130); 
			pushFollow(FOLLOW_suite_in_funcdef132);
			suite();
			state._fsp--;

			System.out.println("found method def "+(NAME1!=null?NAME1.getText():null));
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "funcdef"



	// $ANTLR start "parameters"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:94:1: parameters : LPAREN ( varargslist )? RPAREN ;
	public final void parameters() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:95:5: ( LPAREN ( varargslist )? RPAREN )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:95:9: LPAREN ( varargslist )? RPAREN
			{
			match(input,LPAREN,FOLLOW_LPAREN_in_parameters151); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:95:16: ( varargslist )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==DOUBLESTAR||LA5_0==LPAREN||LA5_0==NAME||LA5_0==STAR) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:95:17: varargslist
					{
					pushFollow(FOLLOW_varargslist_in_parameters154);
					varargslist();
					state._fsp--;

					}
					break;

			}

			match(input,RPAREN,FOLLOW_RPAREN_in_parameters158); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "parameters"



	// $ANTLR start "varargslist"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:98:1: varargslist : ( defparameter ( options {greedy=true; } : COMMA defparameter )* ( COMMA ( STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )? )? | STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME );
	public final void varargslist() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:99:5: ( defparameter ( options {greedy=true; } : COMMA defparameter )* ( COMMA ( STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )? )? | STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )
			int alt11=3;
			switch ( input.LA(1) ) {
			case LPAREN:
			case NAME:
				{
				alt11=1;
				}
				break;
			case STAR:
				{
				alt11=2;
				}
				break;
			case DOUBLESTAR:
				{
				alt11=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}
			switch (alt11) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:99:9: defparameter ( options {greedy=true; } : COMMA defparameter )* ( COMMA ( STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )? )?
					{
					pushFollow(FOLLOW_defparameter_in_varargslist174);
					defparameter();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:99:22: ( options {greedy=true; } : COMMA defparameter )*
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( (LA6_0==COMMA) ) {
							int LA6_1 = input.LA(2);
							if ( (LA6_1==LPAREN||LA6_1==NAME) ) {
								alt6=1;
							}

						}

						switch (alt6) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:99:46: COMMA defparameter
							{
							match(input,COMMA,FOLLOW_COMMA_in_varargslist184); 
							pushFollow(FOLLOW_defparameter_in_varargslist186);
							defparameter();
							state._fsp--;

							}
							break;

						default :
							break loop6;
						}
					}

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:100:9: ( COMMA ( STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )? )?
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==COMMA) ) {
						alt9=1;
					}
					switch (alt9) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:100:10: COMMA ( STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )?
							{
							match(input,COMMA,FOLLOW_COMMA_in_varargslist199); 
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:101:13: ( STAR NAME ( COMMA DOUBLESTAR NAME )? | DOUBLESTAR NAME )?
							int alt8=3;
							int LA8_0 = input.LA(1);
							if ( (LA8_0==STAR) ) {
								alt8=1;
							}
							else if ( (LA8_0==DOUBLESTAR) ) {
								alt8=2;
							}
							switch (alt8) {
								case 1 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:101:15: STAR NAME ( COMMA DOUBLESTAR NAME )?
									{
									match(input,STAR,FOLLOW_STAR_in_varargslist215); 
									match(input,NAME,FOLLOW_NAME_in_varargslist217); 
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:101:25: ( COMMA DOUBLESTAR NAME )?
									int alt7=2;
									int LA7_0 = input.LA(1);
									if ( (LA7_0==COMMA) ) {
										alt7=1;
									}
									switch (alt7) {
										case 1 :
											// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:101:26: COMMA DOUBLESTAR NAME
											{
											match(input,COMMA,FOLLOW_COMMA_in_varargslist220); 
											match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist222); 
											match(input,NAME,FOLLOW_NAME_in_varargslist224); 
											}
											break;

									}

									}
									break;
								case 2 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:102:15: DOUBLESTAR NAME
									{
									match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist242); 
									match(input,NAME,FOLLOW_NAME_in_varargslist244); 
									}
									break;

							}

							}
							break;

					}

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:105:9: STAR NAME ( COMMA DOUBLESTAR NAME )?
					{
					match(input,STAR,FOLLOW_STAR_in_varargslist280); 
					match(input,NAME,FOLLOW_NAME_in_varargslist282); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:105:19: ( COMMA DOUBLESTAR NAME )?
					int alt10=2;
					int LA10_0 = input.LA(1);
					if ( (LA10_0==COMMA) ) {
						alt10=1;
					}
					switch (alt10) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:105:20: COMMA DOUBLESTAR NAME
							{
							match(input,COMMA,FOLLOW_COMMA_in_varargslist285); 
							match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist287); 
							match(input,NAME,FOLLOW_NAME_in_varargslist289); 
							}
							break;

					}

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:106:9: DOUBLESTAR NAME
					{
					match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist301); 
					match(input,NAME,FOLLOW_NAME_in_varargslist303); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "varargslist"



	// $ANTLR start "defparameter"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:109:1: defparameter : fpdef ( ASSIGN test )? ;
	public final void defparameter() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:110:5: ( fpdef ( ASSIGN test )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:110:9: fpdef ( ASSIGN test )?
			{
			pushFollow(FOLLOW_fpdef_in_defparameter322);
			fpdef();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:110:15: ( ASSIGN test )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==ASSIGN) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:110:16: ASSIGN test
					{
					match(input,ASSIGN,FOLLOW_ASSIGN_in_defparameter325); 
					pushFollow(FOLLOW_test_in_defparameter327);
					test();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "defparameter"



	// $ANTLR start "fpdef"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:113:1: fpdef : ( NAME | LPAREN fplist RPAREN );
	public final void fpdef() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:114:5: ( NAME | LPAREN fplist RPAREN )
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==NAME) ) {
				alt13=1;
			}
			else if ( (LA13_0==LPAREN) ) {
				alt13=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}

			switch (alt13) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:114:9: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_fpdef348); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:115:6: LPAREN fplist RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_fpdef355); 
					pushFollow(FOLLOW_fplist_in_fpdef357);
					fplist();
					state._fsp--;

					match(input,RPAREN,FOLLOW_RPAREN_in_fpdef359); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "fpdef"



	// $ANTLR start "fplist"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:118:1: fplist : fpdef ( options {greedy=true; } : COMMA fpdef )* ( COMMA )? ;
	public final void fplist() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:119:5: ( fpdef ( options {greedy=true; } : COMMA fpdef )* ( COMMA )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:119:9: fpdef ( options {greedy=true; } : COMMA fpdef )* ( COMMA )?
			{
			pushFollow(FOLLOW_fpdef_in_fplist375);
			fpdef();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:119:15: ( options {greedy=true; } : COMMA fpdef )*
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( (LA14_0==COMMA) ) {
					int LA14_1 = input.LA(2);
					if ( (LA14_1==LPAREN||LA14_1==NAME) ) {
						alt14=1;
					}

				}

				switch (alt14) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:119:39: COMMA fpdef
					{
					match(input,COMMA,FOLLOW_COMMA_in_fplist385); 
					pushFollow(FOLLOW_fpdef_in_fplist387);
					fpdef();
					state._fsp--;

					}
					break;

				default :
					break loop14;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:119:53: ( COMMA )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==COMMA) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:119:54: COMMA
					{
					match(input,COMMA,FOLLOW_COMMA_in_fplist392); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "fplist"



	// $ANTLR start "stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:123:1: stmt : ( simple_stmt | compound_stmt );
	public final void stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:123:5: ( simple_stmt | compound_stmt )
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==BACKQUOTE||LA16_0==COMPLEX||LA16_0==FLOAT||(LA16_0 >= INT && LA16_0 <= LCURLY)||(LA16_0 >= LONGINT && LA16_0 <= MINUS)||LA16_0==NAME||LA16_0==PLUS||(LA16_0 >= STRING && LA16_0 <= TILDE)||(LA16_0 >= 65 && LA16_0 <= 66)||LA16_0==68||LA16_0==70||LA16_0==74||(LA16_0 >= 77 && LA16_0 <= 78)||LA16_0==80||(LA16_0 >= 83 && LA16_0 <= 84)||(LA16_0 >= 86 && LA16_0 <= 89)||LA16_0==92) ) {
				alt16=1;
			}
			else if ( (LA16_0==67||LA16_0==69||LA16_0==76||LA16_0==79||(LA16_0 >= 90 && LA16_0 <= 91)) ) {
				alt16=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 16, 0, input);
				throw nvae;
			}

			switch (alt16) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:123:7: simple_stmt
					{
					pushFollow(FOLLOW_simple_stmt_in_stmt404);
					simple_stmt();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:124:4: compound_stmt
					{
					pushFollow(FOLLOW_compound_stmt_in_stmt409);
					compound_stmt();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "stmt"



	// $ANTLR start "simple_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:127:1: simple_stmt : small_stmt ( options {greedy=true; } : SEMI small_stmt )* ( SEMI )? NEWLINE ;
	public final void simple_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:128:5: ( small_stmt ( options {greedy=true; } : SEMI small_stmt )* ( SEMI )? NEWLINE )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:128:9: small_stmt ( options {greedy=true; } : SEMI small_stmt )* ( SEMI )? NEWLINE
			{
			pushFollow(FOLLOW_small_stmt_in_simple_stmt425);
			small_stmt();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:128:20: ( options {greedy=true; } : SEMI small_stmt )*
			loop17:
			while (true) {
				int alt17=2;
				int LA17_0 = input.LA(1);
				if ( (LA17_0==SEMI) ) {
					int LA17_1 = input.LA(2);
					if ( (LA17_1==BACKQUOTE||LA17_1==COMPLEX||LA17_1==FLOAT||(LA17_1 >= INT && LA17_1 <= LCURLY)||(LA17_1 >= LONGINT && LA17_1 <= MINUS)||LA17_1==NAME||LA17_1==PLUS||(LA17_1 >= STRING && LA17_1 <= TILDE)||(LA17_1 >= 65 && LA17_1 <= 66)||LA17_1==68||LA17_1==70||LA17_1==74||(LA17_1 >= 77 && LA17_1 <= 78)||LA17_1==80||(LA17_1 >= 83 && LA17_1 <= 84)||(LA17_1 >= 86 && LA17_1 <= 89)||LA17_1==92) ) {
						alt17=1;
					}

				}

				switch (alt17) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:128:44: SEMI small_stmt
					{
					match(input,SEMI,FOLLOW_SEMI_in_simple_stmt435); 
					pushFollow(FOLLOW_small_stmt_in_simple_stmt437);
					small_stmt();
					state._fsp--;

					}
					break;

				default :
					break loop17;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:128:62: ( SEMI )?
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==SEMI) ) {
				alt18=1;
			}
			switch (alt18) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:128:63: SEMI
					{
					match(input,SEMI,FOLLOW_SEMI_in_simple_stmt442); 
					}
					break;

			}

			match(input,NEWLINE,FOLLOW_NEWLINE_in_simple_stmt446); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "simple_stmt"



	// $ANTLR start "small_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:131:1: small_stmt : ( expr_stmt | print_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt );
	public final void small_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:131:11: ( expr_stmt | print_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt )
			int alt19=9;
			switch ( input.LA(1) ) {
			case BACKQUOTE:
			case COMPLEX:
			case FLOAT:
			case INT:
			case LBRACK:
			case LCURLY:
			case LONGINT:
			case LPAREN:
			case MINUS:
			case NAME:
			case PLUS:
			case STRING:
			case TILDE:
			case 83:
			case 84:
				{
				alt19=1;
				}
				break;
			case 87:
				{
				alt19=2;
				}
				break;
			case 70:
				{
				alt19=3;
				}
				break;
			case 86:
				{
				alt19=4;
				}
				break;
			case 66:
			case 68:
			case 88:
			case 89:
			case 92:
				{
				alt19=5;
				}
				break;
			case 77:
			case 80:
				{
				alt19=6;
				}
				break;
			case 78:
				{
				alt19=7;
				}
				break;
			case 74:
				{
				alt19=8;
				}
				break;
			case 65:
				{
				alt19=9;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 19, 0, input);
				throw nvae;
			}
			switch (alt19) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:131:13: expr_stmt
					{
					pushFollow(FOLLOW_expr_stmt_in_small_stmt455);
					expr_stmt();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:132:4: print_stmt
					{
					pushFollow(FOLLOW_print_stmt_in_small_stmt460);
					print_stmt();
					state._fsp--;

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:133:4: del_stmt
					{
					pushFollow(FOLLOW_del_stmt_in_small_stmt465);
					del_stmt();
					state._fsp--;

					}
					break;
				case 4 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:134:4: pass_stmt
					{
					pushFollow(FOLLOW_pass_stmt_in_small_stmt470);
					pass_stmt();
					state._fsp--;

					}
					break;
				case 5 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:135:4: flow_stmt
					{
					pushFollow(FOLLOW_flow_stmt_in_small_stmt475);
					flow_stmt();
					state._fsp--;

					}
					break;
				case 6 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:136:4: import_stmt
					{
					pushFollow(FOLLOW_import_stmt_in_small_stmt480);
					import_stmt();
					state._fsp--;

					}
					break;
				case 7 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:137:4: global_stmt
					{
					pushFollow(FOLLOW_global_stmt_in_small_stmt485);
					global_stmt();
					state._fsp--;

					}
					break;
				case 8 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:138:4: exec_stmt
					{
					pushFollow(FOLLOW_exec_stmt_in_small_stmt490);
					exec_stmt();
					state._fsp--;

					}
					break;
				case 9 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:139:4: assert_stmt
					{
					pushFollow(FOLLOW_assert_stmt_in_small_stmt495);
					assert_stmt();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "small_stmt"



	// $ANTLR start "expr_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:142:1: expr_stmt : testlist ( augassign testlist | ( ASSIGN testlist )+ )? ;
	public final void expr_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:143:2: ( testlist ( augassign testlist | ( ASSIGN testlist )+ )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:143:4: testlist ( augassign testlist | ( ASSIGN testlist )+ )?
			{
			pushFollow(FOLLOW_testlist_in_expr_stmt506);
			testlist();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:144:3: ( augassign testlist | ( ASSIGN testlist )+ )?
			int alt21=3;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==AMPEREQUAL||LA21_0==CIRCUMFLEXEQUAL||LA21_0==DOUBLESLASHEQUAL||LA21_0==DOUBLESTAREQUAL||LA21_0==LEFTSHIFTEQUAL||LA21_0==MINUSEQUAL||LA21_0==PERCENTEQUAL||LA21_0==PLUSEQUAL||LA21_0==RIGHTSHIFTEQUAL||LA21_0==SLASHEQUAL||LA21_0==STAREQUAL||LA21_0==VBAREQUAL) ) {
				alt21=1;
			}
			else if ( (LA21_0==ASSIGN) ) {
				alt21=2;
			}
			switch (alt21) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:144:5: augassign testlist
					{
					pushFollow(FOLLOW_augassign_in_expr_stmt512);
					augassign();
					state._fsp--;

					pushFollow(FOLLOW_testlist_in_expr_stmt514);
					testlist();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:145:5: ( ASSIGN testlist )+
					{
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:145:5: ( ASSIGN testlist )+
					int cnt20=0;
					loop20:
					while (true) {
						int alt20=2;
						int LA20_0 = input.LA(1);
						if ( (LA20_0==ASSIGN) ) {
							alt20=1;
						}

						switch (alt20) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:145:6: ASSIGN testlist
							{
							match(input,ASSIGN,FOLLOW_ASSIGN_in_expr_stmt521); 
							pushFollow(FOLLOW_testlist_in_expr_stmt523);
							testlist();
							state._fsp--;

							}
							break;

						default :
							if ( cnt20 >= 1 ) break loop20;
							EarlyExitException eee = new EarlyExitException(20, input);
							throw eee;
						}
						cnt20++;
					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "expr_stmt"



	// $ANTLR start "augassign"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:149:1: augassign : ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL );
	public final void augassign() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:150:5: ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:
			{
			if ( input.LA(1)==AMPEREQUAL||input.LA(1)==CIRCUMFLEXEQUAL||input.LA(1)==DOUBLESLASHEQUAL||input.LA(1)==DOUBLESTAREQUAL||input.LA(1)==LEFTSHIFTEQUAL||input.LA(1)==MINUSEQUAL||input.LA(1)==PERCENTEQUAL||input.LA(1)==PLUSEQUAL||input.LA(1)==RIGHTSHIFTEQUAL||input.LA(1)==SLASHEQUAL||input.LA(1)==STAREQUAL||input.LA(1)==VBAREQUAL ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "augassign"



	// $ANTLR start "print_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:164:1: print_stmt : 'print' ( testlist | RIGHTSHIFT testlist )? ;
	public final void print_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:164:11: ( 'print' ( testlist | RIGHTSHIFT testlist )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:165:9: 'print' ( testlist | RIGHTSHIFT testlist )?
			{
			match(input,87,FOLLOW_87_in_print_stmt616); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:166:9: ( testlist | RIGHTSHIFT testlist )?
			int alt22=3;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==BACKQUOTE||LA22_0==COMPLEX||LA22_0==FLOAT||(LA22_0 >= INT && LA22_0 <= LCURLY)||(LA22_0 >= LONGINT && LA22_0 <= MINUS)||LA22_0==NAME||LA22_0==PLUS||(LA22_0 >= STRING && LA22_0 <= TILDE)||(LA22_0 >= 83 && LA22_0 <= 84)) ) {
				alt22=1;
			}
			else if ( (LA22_0==RIGHTSHIFT) ) {
				alt22=2;
			}
			switch (alt22) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:166:13: testlist
					{
					pushFollow(FOLLOW_testlist_in_print_stmt630);
					testlist();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:167:13: RIGHTSHIFT testlist
					{
					match(input,RIGHTSHIFT,FOLLOW_RIGHTSHIFT_in_print_stmt644); 
					pushFollow(FOLLOW_testlist_in_print_stmt646);
					testlist();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "print_stmt"



	// $ANTLR start "del_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:171:1: del_stmt : 'del' exprlist ;
	public final void del_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:171:9: ( 'del' exprlist )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:171:11: 'del' exprlist
			{
			match(input,70,FOLLOW_70_in_del_stmt666); 
			pushFollow(FOLLOW_exprlist_in_del_stmt668);
			exprlist();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "del_stmt"



	// $ANTLR start "pass_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:174:1: pass_stmt : 'pass' ;
	public final void pass_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:174:10: ( 'pass' )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:174:12: 'pass'
			{
			match(input,86,FOLLOW_86_in_pass_stmt677); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "pass_stmt"



	// $ANTLR start "flow_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:177:1: flow_stmt : ( break_stmt | continue_stmt | return_stmt | raise_stmt | yield_stmt );
	public final void flow_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:177:10: ( break_stmt | continue_stmt | return_stmt | raise_stmt | yield_stmt )
			int alt23=5;
			switch ( input.LA(1) ) {
			case 66:
				{
				alt23=1;
				}
				break;
			case 68:
				{
				alt23=2;
				}
				break;
			case 89:
				{
				alt23=3;
				}
				break;
			case 88:
				{
				alt23=4;
				}
				break;
			case 92:
				{
				alt23=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 23, 0, input);
				throw nvae;
			}
			switch (alt23) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:177:12: break_stmt
					{
					pushFollow(FOLLOW_break_stmt_in_flow_stmt686);
					break_stmt();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:178:4: continue_stmt
					{
					pushFollow(FOLLOW_continue_stmt_in_flow_stmt691);
					continue_stmt();
					state._fsp--;

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:179:4: return_stmt
					{
					pushFollow(FOLLOW_return_stmt_in_flow_stmt696);
					return_stmt();
					state._fsp--;

					}
					break;
				case 4 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:180:4: raise_stmt
					{
					pushFollow(FOLLOW_raise_stmt_in_flow_stmt701);
					raise_stmt();
					state._fsp--;

					}
					break;
				case 5 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:181:4: yield_stmt
					{
					pushFollow(FOLLOW_yield_stmt_in_flow_stmt706);
					yield_stmt();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "flow_stmt"



	// $ANTLR start "break_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:184:1: break_stmt : 'break' ;
	public final void break_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:184:11: ( 'break' )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:184:13: 'break'
			{
			match(input,66,FOLLOW_66_in_break_stmt715); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "break_stmt"



	// $ANTLR start "continue_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:187:1: continue_stmt : 'continue' ;
	public final void continue_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:187:14: ( 'continue' )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:187:16: 'continue'
			{
			match(input,68,FOLLOW_68_in_continue_stmt724); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "continue_stmt"



	// $ANTLR start "return_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:190:1: return_stmt : 'return' ( testlist )? ;
	public final void return_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:190:12: ( 'return' ( testlist )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:190:14: 'return' ( testlist )?
			{
			match(input,89,FOLLOW_89_in_return_stmt733); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:190:23: ( testlist )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( (LA24_0==BACKQUOTE||LA24_0==COMPLEX||LA24_0==FLOAT||(LA24_0 >= INT && LA24_0 <= LCURLY)||(LA24_0 >= LONGINT && LA24_0 <= MINUS)||LA24_0==NAME||LA24_0==PLUS||(LA24_0 >= STRING && LA24_0 <= TILDE)||(LA24_0 >= 83 && LA24_0 <= 84)) ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:190:24: testlist
					{
					pushFollow(FOLLOW_testlist_in_return_stmt736);
					testlist();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "return_stmt"



	// $ANTLR start "yield_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:193:1: yield_stmt : 'yield' testlist ;
	public final void yield_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:193:11: ( 'yield' testlist )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:193:13: 'yield' testlist
			{
			match(input,92,FOLLOW_92_in_yield_stmt747); 
			pushFollow(FOLLOW_testlist_in_yield_stmt749);
			testlist();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "yield_stmt"



	// $ANTLR start "raise_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:1: raise_stmt : 'raise' ( test ( COMMA test ( COMMA test )? )? )? ;
	public final void raise_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:11: ( 'raise' ( test ( COMMA test ( COMMA test )? )? )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:13: 'raise' ( test ( COMMA test ( COMMA test )? )? )?
			{
			match(input,88,FOLLOW_88_in_raise_stmt758); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:21: ( test ( COMMA test ( COMMA test )? )? )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==BACKQUOTE||LA27_0==COMPLEX||LA27_0==FLOAT||(LA27_0 >= INT && LA27_0 <= LCURLY)||(LA27_0 >= LONGINT && LA27_0 <= MINUS)||LA27_0==NAME||LA27_0==PLUS||(LA27_0 >= STRING && LA27_0 <= TILDE)||(LA27_0 >= 83 && LA27_0 <= 84)) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:22: test ( COMMA test ( COMMA test )? )?
					{
					pushFollow(FOLLOW_test_in_raise_stmt761);
					test();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:27: ( COMMA test ( COMMA test )? )?
					int alt26=2;
					int LA26_0 = input.LA(1);
					if ( (LA26_0==COMMA) ) {
						alt26=1;
					}
					switch (alt26) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:28: COMMA test ( COMMA test )?
							{
							match(input,COMMA,FOLLOW_COMMA_in_raise_stmt764); 
							pushFollow(FOLLOW_test_in_raise_stmt766);
							test();
							state._fsp--;

							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:39: ( COMMA test )?
							int alt25=2;
							int LA25_0 = input.LA(1);
							if ( (LA25_0==COMMA) ) {
								alt25=1;
							}
							switch (alt25) {
								case 1 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:196:40: COMMA test
									{
									match(input,COMMA,FOLLOW_COMMA_in_raise_stmt769); 
									pushFollow(FOLLOW_test_in_raise_stmt771);
									test();
									state._fsp--;

									}
									break;

							}

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "raise_stmt"



	// $ANTLR start "import_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:199:1: import_stmt : ( 'import' dotted_as_name ( COMMA dotted_as_name )* | 'from' dotted_name 'import' ( STAR | import_as_name ( COMMA import_as_name )* ) );
	public final void import_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:200:5: ( 'import' dotted_as_name ( COMMA dotted_as_name )* | 'from' dotted_name 'import' ( STAR | import_as_name ( COMMA import_as_name )* ) )
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==80) ) {
				alt31=1;
			}
			else if ( (LA31_0==77) ) {
				alt31=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 31, 0, input);
				throw nvae;
			}

			switch (alt31) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:200:9: 'import' dotted_as_name ( COMMA dotted_as_name )*
					{
					match(input,80,FOLLOW_80_in_import_stmt793); 
					pushFollow(FOLLOW_dotted_as_name_in_import_stmt795);
					dotted_as_name();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:200:33: ( COMMA dotted_as_name )*
					loop28:
					while (true) {
						int alt28=2;
						int LA28_0 = input.LA(1);
						if ( (LA28_0==COMMA) ) {
							alt28=1;
						}

						switch (alt28) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:200:34: COMMA dotted_as_name
							{
							match(input,COMMA,FOLLOW_COMMA_in_import_stmt798); 
							pushFollow(FOLLOW_dotted_as_name_in_import_stmt800);
							dotted_as_name();
							state._fsp--;

							}
							break;

						default :
							break loop28;
						}
					}

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:201:6: 'from' dotted_name 'import' ( STAR | import_as_name ( COMMA import_as_name )* )
					{
					match(input,77,FOLLOW_77_in_import_stmt809); 
					pushFollow(FOLLOW_dotted_name_in_import_stmt811);
					dotted_name();
					state._fsp--;

					match(input,80,FOLLOW_80_in_import_stmt813); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:202:9: ( STAR | import_as_name ( COMMA import_as_name )* )
					int alt30=2;
					int LA30_0 = input.LA(1);
					if ( (LA30_0==STAR) ) {
						alt30=1;
					}
					else if ( (LA30_0==NAME) ) {
						alt30=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 30, 0, input);
						throw nvae;
					}

					switch (alt30) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:202:10: STAR
							{
							match(input,STAR,FOLLOW_STAR_in_import_stmt824); 
							}
							break;
						case 2 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:202:17: import_as_name ( COMMA import_as_name )*
							{
							pushFollow(FOLLOW_import_as_name_in_import_stmt828);
							import_as_name();
							state._fsp--;

							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:202:32: ( COMMA import_as_name )*
							loop29:
							while (true) {
								int alt29=2;
								int LA29_0 = input.LA(1);
								if ( (LA29_0==COMMA) ) {
									alt29=1;
								}

								switch (alt29) {
								case 1 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:202:33: COMMA import_as_name
									{
									match(input,COMMA,FOLLOW_COMMA_in_import_stmt831); 
									pushFollow(FOLLOW_import_as_name_in_import_stmt833);
									import_as_name();
									state._fsp--;

									}
									break;

								default :
									break loop29;
								}
							}

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "import_stmt"



	// $ANTLR start "import_as_name"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:205:1: import_as_name : NAME ( NAME NAME )? ;
	public final void import_as_name() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:206:5: ( NAME ( NAME NAME )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:206:9: NAME ( NAME NAME )?
			{
			match(input,NAME,FOLLOW_NAME_in_import_as_name852); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:206:14: ( NAME NAME )?
			int alt32=2;
			int LA32_0 = input.LA(1);
			if ( (LA32_0==NAME) ) {
				alt32=1;
			}
			switch (alt32) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:206:15: NAME NAME
					{
					match(input,NAME,FOLLOW_NAME_in_import_as_name855); 
					match(input,NAME,FOLLOW_NAME_in_import_as_name857); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "import_as_name"



	// $ANTLR start "dotted_as_name"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:209:1: dotted_as_name : dotted_name ( NAME NAME )? ;
	public final void dotted_as_name() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:209:15: ( dotted_name ( NAME NAME )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:209:17: dotted_name ( NAME NAME )?
			{
			pushFollow(FOLLOW_dotted_name_in_dotted_as_name868);
			dotted_name();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:209:29: ( NAME NAME )?
			int alt33=2;
			int LA33_0 = input.LA(1);
			if ( (LA33_0==NAME) ) {
				alt33=1;
			}
			switch (alt33) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:209:30: NAME NAME
					{
					match(input,NAME,FOLLOW_NAME_in_dotted_as_name871); 
					match(input,NAME,FOLLOW_NAME_in_dotted_as_name873); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "dotted_as_name"



	// $ANTLR start "dotted_name"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:212:1: dotted_name : NAME ( DOT NAME )* ;
	public final void dotted_name() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:212:12: ( NAME ( DOT NAME )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:212:14: NAME ( DOT NAME )*
			{
			match(input,NAME,FOLLOW_NAME_in_dotted_name884); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:212:19: ( DOT NAME )*
			loop34:
			while (true) {
				int alt34=2;
				int LA34_0 = input.LA(1);
				if ( (LA34_0==DOT) ) {
					alt34=1;
				}

				switch (alt34) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:212:20: DOT NAME
					{
					match(input,DOT,FOLLOW_DOT_in_dotted_name887); 
					match(input,NAME,FOLLOW_NAME_in_dotted_name889); 
					}
					break;

				default :
					break loop34;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "dotted_name"



	// $ANTLR start "global_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:215:1: global_stmt : 'global' NAME ( COMMA NAME )* ;
	public final void global_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:215:12: ( 'global' NAME ( COMMA NAME )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:215:14: 'global' NAME ( COMMA NAME )*
			{
			match(input,78,FOLLOW_78_in_global_stmt900); 
			match(input,NAME,FOLLOW_NAME_in_global_stmt902); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:215:28: ( COMMA NAME )*
			loop35:
			while (true) {
				int alt35=2;
				int LA35_0 = input.LA(1);
				if ( (LA35_0==COMMA) ) {
					alt35=1;
				}

				switch (alt35) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:215:29: COMMA NAME
					{
					match(input,COMMA,FOLLOW_COMMA_in_global_stmt905); 
					match(input,NAME,FOLLOW_NAME_in_global_stmt907); 
					}
					break;

				default :
					break loop35;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "global_stmt"



	// $ANTLR start "exec_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:1: exec_stmt : 'exec' expr ( 'in' test ( COMMA test )? )? ;
	public final void exec_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:10: ( 'exec' expr ( 'in' test ( COMMA test )? )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:12: 'exec' expr ( 'in' test ( COMMA test )? )?
			{
			match(input,74,FOLLOW_74_in_exec_stmt918); 
			pushFollow(FOLLOW_expr_in_exec_stmt920);
			expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:24: ( 'in' test ( COMMA test )? )?
			int alt37=2;
			int LA37_0 = input.LA(1);
			if ( (LA37_0==81) ) {
				alt37=1;
			}
			switch (alt37) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:25: 'in' test ( COMMA test )?
					{
					match(input,81,FOLLOW_81_in_exec_stmt923); 
					pushFollow(FOLLOW_test_in_exec_stmt925);
					test();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:35: ( COMMA test )?
					int alt36=2;
					int LA36_0 = input.LA(1);
					if ( (LA36_0==COMMA) ) {
						alt36=1;
					}
					switch (alt36) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:218:36: COMMA test
							{
							match(input,COMMA,FOLLOW_COMMA_in_exec_stmt928); 
							pushFollow(FOLLOW_test_in_exec_stmt930);
							test();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exec_stmt"



	// $ANTLR start "assert_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:221:1: assert_stmt : 'assert' test ( COMMA test )? ;
	public final void assert_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:221:12: ( 'assert' test ( COMMA test )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:221:14: 'assert' test ( COMMA test )?
			{
			match(input,65,FOLLOW_65_in_assert_stmt943); 
			pushFollow(FOLLOW_test_in_assert_stmt945);
			test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:221:28: ( COMMA test )?
			int alt38=2;
			int LA38_0 = input.LA(1);
			if ( (LA38_0==COMMA) ) {
				alt38=1;
			}
			switch (alt38) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:221:29: COMMA test
					{
					match(input,COMMA,FOLLOW_COMMA_in_assert_stmt948); 
					pushFollow(FOLLOW_test_in_assert_stmt950);
					test();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "assert_stmt"



	// $ANTLR start "compound_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:225:1: compound_stmt : ( if_stmt | while_stmt | for_stmt | try_stmt | funcdef | classdef );
	public final void compound_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:225:14: ( if_stmt | while_stmt | for_stmt | try_stmt | funcdef | classdef )
			int alt39=6;
			switch ( input.LA(1) ) {
			case 79:
				{
				alt39=1;
				}
				break;
			case 91:
				{
				alt39=2;
				}
				break;
			case 76:
				{
				alt39=3;
				}
				break;
			case 90:
				{
				alt39=4;
				}
				break;
			case 69:
				{
				alt39=5;
				}
				break;
			case 67:
				{
				alt39=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 39, 0, input);
				throw nvae;
			}
			switch (alt39) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:225:16: if_stmt
					{
					pushFollow(FOLLOW_if_stmt_in_compound_stmt962);
					if_stmt();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:226:4: while_stmt
					{
					pushFollow(FOLLOW_while_stmt_in_compound_stmt967);
					while_stmt();
					state._fsp--;

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:227:4: for_stmt
					{
					pushFollow(FOLLOW_for_stmt_in_compound_stmt972);
					for_stmt();
					state._fsp--;

					}
					break;
				case 4 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:228:4: try_stmt
					{
					pushFollow(FOLLOW_try_stmt_in_compound_stmt977);
					try_stmt();
					state._fsp--;

					}
					break;
				case 5 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:229:4: funcdef
					{
					pushFollow(FOLLOW_funcdef_in_compound_stmt982);
					funcdef();
					state._fsp--;

					}
					break;
				case 6 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:230:4: classdef
					{
					pushFollow(FOLLOW_classdef_in_compound_stmt987);
					classdef();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "compound_stmt"



	// $ANTLR start "if_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:1: if_stmt : 'if' test COLON suite ( 'elif' test COLON suite )* ( 'else' COLON suite )? ;
	public final void if_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:8: ( 'if' test COLON suite ( 'elif' test COLON suite )* ( 'else' COLON suite )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:10: 'if' test COLON suite ( 'elif' test COLON suite )* ( 'else' COLON suite )?
			{
			match(input,79,FOLLOW_79_in_if_stmt996); 
			pushFollow(FOLLOW_test_in_if_stmt998);
			test();
			state._fsp--;

			match(input,COLON,FOLLOW_COLON_in_if_stmt1000); 
			pushFollow(FOLLOW_suite_in_if_stmt1002);
			suite();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:32: ( 'elif' test COLON suite )*
			loop40:
			while (true) {
				int alt40=2;
				int LA40_0 = input.LA(1);
				if ( (LA40_0==71) ) {
					alt40=1;
				}

				switch (alt40) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:33: 'elif' test COLON suite
					{
					match(input,71,FOLLOW_71_in_if_stmt1005); 
					pushFollow(FOLLOW_test_in_if_stmt1007);
					test();
					state._fsp--;

					match(input,COLON,FOLLOW_COLON_in_if_stmt1009); 
					pushFollow(FOLLOW_suite_in_if_stmt1011);
					suite();
					state._fsp--;

					}
					break;

				default :
					break loop40;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:59: ( 'else' COLON suite )?
			int alt41=2;
			int LA41_0 = input.LA(1);
			if ( (LA41_0==72) ) {
				alt41=1;
			}
			switch (alt41) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:233:60: 'else' COLON suite
					{
					match(input,72,FOLLOW_72_in_if_stmt1016); 
					match(input,COLON,FOLLOW_COLON_in_if_stmt1018); 
					pushFollow(FOLLOW_suite_in_if_stmt1020);
					suite();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "if_stmt"



	// $ANTLR start "while_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:236:1: while_stmt : 'while' test COLON suite ( 'else' COLON suite )? ;
	public final void while_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:236:11: ( 'while' test COLON suite ( 'else' COLON suite )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:236:13: 'while' test COLON suite ( 'else' COLON suite )?
			{
			match(input,91,FOLLOW_91_in_while_stmt1031); 
			pushFollow(FOLLOW_test_in_while_stmt1033);
			test();
			state._fsp--;

			match(input,COLON,FOLLOW_COLON_in_while_stmt1035); 
			pushFollow(FOLLOW_suite_in_while_stmt1037);
			suite();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:236:38: ( 'else' COLON suite )?
			int alt42=2;
			int LA42_0 = input.LA(1);
			if ( (LA42_0==72) ) {
				alt42=1;
			}
			switch (alt42) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:236:39: 'else' COLON suite
					{
					match(input,72,FOLLOW_72_in_while_stmt1040); 
					match(input,COLON,FOLLOW_COLON_in_while_stmt1042); 
					pushFollow(FOLLOW_suite_in_while_stmt1044);
					suite();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "while_stmt"



	// $ANTLR start "for_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:239:1: for_stmt : 'for' exprlist 'in' testlist COLON suite ( 'else' COLON suite )? ;
	public final void for_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:239:9: ( 'for' exprlist 'in' testlist COLON suite ( 'else' COLON suite )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:239:11: 'for' exprlist 'in' testlist COLON suite ( 'else' COLON suite )?
			{
			match(input,76,FOLLOW_76_in_for_stmt1055); 
			pushFollow(FOLLOW_exprlist_in_for_stmt1057);
			exprlist();
			state._fsp--;

			match(input,81,FOLLOW_81_in_for_stmt1059); 
			pushFollow(FOLLOW_testlist_in_for_stmt1061);
			testlist();
			state._fsp--;

			match(input,COLON,FOLLOW_COLON_in_for_stmt1063); 
			pushFollow(FOLLOW_suite_in_for_stmt1065);
			suite();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:239:52: ( 'else' COLON suite )?
			int alt43=2;
			int LA43_0 = input.LA(1);
			if ( (LA43_0==72) ) {
				alt43=1;
			}
			switch (alt43) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:239:53: 'else' COLON suite
					{
					match(input,72,FOLLOW_72_in_for_stmt1068); 
					match(input,COLON,FOLLOW_COLON_in_for_stmt1070); 
					pushFollow(FOLLOW_suite_in_for_stmt1072);
					suite();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "for_stmt"



	// $ANTLR start "try_stmt"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:242:1: try_stmt : 'try' COLON suite ( ( except_clause COLON suite )+ ( 'else' COLON suite )? | 'finally' COLON suite ) ;
	public final void try_stmt() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:243:5: ( 'try' COLON suite ( ( except_clause COLON suite )+ ( 'else' COLON suite )? | 'finally' COLON suite ) )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:243:9: 'try' COLON suite ( ( except_clause COLON suite )+ ( 'else' COLON suite )? | 'finally' COLON suite )
			{
			match(input,90,FOLLOW_90_in_try_stmt1090); 
			match(input,COLON,FOLLOW_COLON_in_try_stmt1092); 
			pushFollow(FOLLOW_suite_in_try_stmt1094);
			suite();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:244:9: ( ( except_clause COLON suite )+ ( 'else' COLON suite )? | 'finally' COLON suite )
			int alt46=2;
			int LA46_0 = input.LA(1);
			if ( (LA46_0==73) ) {
				alt46=1;
			}
			else if ( (LA46_0==75) ) {
				alt46=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 46, 0, input);
				throw nvae;
			}

			switch (alt46) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:244:13: ( except_clause COLON suite )+ ( 'else' COLON suite )?
					{
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:244:13: ( except_clause COLON suite )+
					int cnt44=0;
					loop44:
					while (true) {
						int alt44=2;
						int LA44_0 = input.LA(1);
						if ( (LA44_0==73) ) {
							alt44=1;
						}

						switch (alt44) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:244:14: except_clause COLON suite
							{
							pushFollow(FOLLOW_except_clause_in_try_stmt1109);
							except_clause();
							state._fsp--;

							match(input,COLON,FOLLOW_COLON_in_try_stmt1111); 
							pushFollow(FOLLOW_suite_in_try_stmt1113);
							suite();
							state._fsp--;

							}
							break;

						default :
							if ( cnt44 >= 1 ) break loop44;
							EarlyExitException eee = new EarlyExitException(44, input);
							throw eee;
						}
						cnt44++;
					}

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:244:42: ( 'else' COLON suite )?
					int alt45=2;
					int LA45_0 = input.LA(1);
					if ( (LA45_0==72) ) {
						alt45=1;
					}
					switch (alt45) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:244:43: 'else' COLON suite
							{
							match(input,72,FOLLOW_72_in_try_stmt1118); 
							match(input,COLON,FOLLOW_COLON_in_try_stmt1120); 
							pushFollow(FOLLOW_suite_in_try_stmt1122);
							suite();
							state._fsp--;

							}
							break;

					}

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:245:13: 'finally' COLON suite
					{
					match(input,75,FOLLOW_75_in_try_stmt1138); 
					match(input,COLON,FOLLOW_COLON_in_try_stmt1140); 
					pushFollow(FOLLOW_suite_in_try_stmt1142);
					suite();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "try_stmt"



	// $ANTLR start "except_clause"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:1: except_clause : 'except' ( test ( COMMA test )? )? ;
	public final void except_clause() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:14: ( 'except' ( test ( COMMA test )? )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:16: 'except' ( test ( COMMA test )? )?
			{
			match(input,73,FOLLOW_73_in_except_clause1161); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:25: ( test ( COMMA test )? )?
			int alt48=2;
			int LA48_0 = input.LA(1);
			if ( (LA48_0==BACKQUOTE||LA48_0==COMPLEX||LA48_0==FLOAT||(LA48_0 >= INT && LA48_0 <= LCURLY)||(LA48_0 >= LONGINT && LA48_0 <= MINUS)||LA48_0==NAME||LA48_0==PLUS||(LA48_0 >= STRING && LA48_0 <= TILDE)||(LA48_0 >= 83 && LA48_0 <= 84)) ) {
				alt48=1;
			}
			switch (alt48) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:26: test ( COMMA test )?
					{
					pushFollow(FOLLOW_test_in_except_clause1164);
					test();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:31: ( COMMA test )?
					int alt47=2;
					int LA47_0 = input.LA(1);
					if ( (LA47_0==COMMA) ) {
						alt47=1;
					}
					switch (alt47) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:249:32: COMMA test
							{
							match(input,COMMA,FOLLOW_COMMA_in_except_clause1167); 
							pushFollow(FOLLOW_test_in_except_clause1169);
							test();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "except_clause"



	// $ANTLR start "suite"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:252:1: suite : ( simple_stmt | NEWLINE INDENT ( stmt )+ DEDENT );
	public final void suite() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:252:6: ( simple_stmt | NEWLINE INDENT ( stmt )+ DEDENT )
			int alt50=2;
			int LA50_0 = input.LA(1);
			if ( (LA50_0==BACKQUOTE||LA50_0==COMPLEX||LA50_0==FLOAT||(LA50_0 >= INT && LA50_0 <= LCURLY)||(LA50_0 >= LONGINT && LA50_0 <= MINUS)||LA50_0==NAME||LA50_0==PLUS||(LA50_0 >= STRING && LA50_0 <= TILDE)||(LA50_0 >= 65 && LA50_0 <= 66)||LA50_0==68||LA50_0==70||LA50_0==74||(LA50_0 >= 77 && LA50_0 <= 78)||LA50_0==80||(LA50_0 >= 83 && LA50_0 <= 84)||(LA50_0 >= 86 && LA50_0 <= 89)||LA50_0==92) ) {
				alt50=1;
			}
			else if ( (LA50_0==NEWLINE) ) {
				alt50=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 50, 0, input);
				throw nvae;
			}

			switch (alt50) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:252:8: simple_stmt
					{
					pushFollow(FOLLOW_simple_stmt_in_suite1182);
					simple_stmt();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:253:4: NEWLINE INDENT ( stmt )+ DEDENT
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_suite1187); 
					match(input,INDENT,FOLLOW_INDENT_in_suite1189); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:253:19: ( stmt )+
					int cnt49=0;
					loop49:
					while (true) {
						int alt49=2;
						int LA49_0 = input.LA(1);
						if ( (LA49_0==BACKQUOTE||LA49_0==COMPLEX||LA49_0==FLOAT||(LA49_0 >= INT && LA49_0 <= LCURLY)||(LA49_0 >= LONGINT && LA49_0 <= MINUS)||LA49_0==NAME||LA49_0==PLUS||(LA49_0 >= STRING && LA49_0 <= TILDE)||(LA49_0 >= 65 && LA49_0 <= 70)||LA49_0==74||(LA49_0 >= 76 && LA49_0 <= 80)||(LA49_0 >= 83 && LA49_0 <= 84)||(LA49_0 >= 86 && LA49_0 <= 92)) ) {
							alt49=1;
						}

						switch (alt49) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:253:20: stmt
							{
							pushFollow(FOLLOW_stmt_in_suite1192);
							stmt();
							state._fsp--;

							}
							break;

						default :
							if ( cnt49 >= 1 ) break loop49;
							EarlyExitException eee = new EarlyExitException(49, input);
							throw eee;
						}
						cnt49++;
					}

					match(input,DEDENT,FOLLOW_DEDENT_in_suite1196); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "suite"



	// $ANTLR start "test"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:257:1: test : ( and_test ( 'or' and_test )* | lambdef );
	public final void test() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:257:5: ( and_test ( 'or' and_test )* | lambdef )
			int alt52=2;
			int LA52_0 = input.LA(1);
			if ( (LA52_0==BACKQUOTE||LA52_0==COMPLEX||LA52_0==FLOAT||(LA52_0 >= INT && LA52_0 <= LCURLY)||(LA52_0 >= LONGINT && LA52_0 <= MINUS)||LA52_0==NAME||LA52_0==PLUS||(LA52_0 >= STRING && LA52_0 <= TILDE)||LA52_0==84) ) {
				alt52=1;
			}
			else if ( (LA52_0==83) ) {
				alt52=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 52, 0, input);
				throw nvae;
			}

			switch (alt52) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:257:7: and_test ( 'or' and_test )*
					{
					pushFollow(FOLLOW_and_test_in_test1206);
					and_test();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:257:16: ( 'or' and_test )*
					loop51:
					while (true) {
						int alt51=2;
						int LA51_0 = input.LA(1);
						if ( (LA51_0==85) ) {
							alt51=1;
						}

						switch (alt51) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:257:17: 'or' and_test
							{
							match(input,85,FOLLOW_85_in_test1209); 
							pushFollow(FOLLOW_and_test_in_test1211);
							and_test();
							state._fsp--;

							}
							break;

						default :
							break loop51;
						}
					}

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:258:4: lambdef
					{
					pushFollow(FOLLOW_lambdef_in_test1218);
					lambdef();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "test"



	// $ANTLR start "and_test"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:261:1: and_test : not_test ( 'and' not_test )* ;
	public final void and_test() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:262:2: ( not_test ( 'and' not_test )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:262:4: not_test ( 'and' not_test )*
			{
			pushFollow(FOLLOW_not_test_in_and_test1229);
			not_test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:262:13: ( 'and' not_test )*
			loop53:
			while (true) {
				int alt53=2;
				int LA53_0 = input.LA(1);
				if ( (LA53_0==64) ) {
					alt53=1;
				}

				switch (alt53) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:262:14: 'and' not_test
					{
					match(input,64,FOLLOW_64_in_and_test1232); 
					pushFollow(FOLLOW_not_test_in_and_test1234);
					not_test();
					state._fsp--;

					}
					break;

				default :
					break loop53;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "and_test"



	// $ANTLR start "not_test"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:265:1: not_test : ( 'not' not_test | comparison );
	public final void not_test() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:266:2: ( 'not' not_test | comparison )
			int alt54=2;
			int LA54_0 = input.LA(1);
			if ( (LA54_0==84) ) {
				alt54=1;
			}
			else if ( (LA54_0==BACKQUOTE||LA54_0==COMPLEX||LA54_0==FLOAT||(LA54_0 >= INT && LA54_0 <= LCURLY)||(LA54_0 >= LONGINT && LA54_0 <= MINUS)||LA54_0==NAME||LA54_0==PLUS||(LA54_0 >= STRING && LA54_0 <= TILDE)) ) {
				alt54=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 54, 0, input);
				throw nvae;
			}

			switch (alt54) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:266:4: 'not' not_test
					{
					match(input,84,FOLLOW_84_in_not_test1247); 
					pushFollow(FOLLOW_not_test_in_not_test1249);
					not_test();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:267:4: comparison
					{
					pushFollow(FOLLOW_comparison_in_not_test1254);
					comparison();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "not_test"



	// $ANTLR start "comparison"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:270:1: comparison : expr ( comp_op expr )* ;
	public final void comparison() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:270:11: ( expr ( comp_op expr )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:270:13: expr ( comp_op expr )*
			{
			pushFollow(FOLLOW_expr_in_comparison1263);
			expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:270:18: ( comp_op expr )*
			loop55:
			while (true) {
				int alt55=2;
				int LA55_0 = input.LA(1);
				if ( (LA55_0==ALT_NOTEQUAL||LA55_0==EQUAL||(LA55_0 >= GREATER && LA55_0 <= GREATEREQUAL)||(LA55_0 >= LESS && LA55_0 <= LESSEQUAL)||LA55_0==NOTEQUAL||(LA55_0 >= 81 && LA55_0 <= 82)||LA55_0==84) ) {
					alt55=1;
				}

				switch (alt55) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:270:19: comp_op expr
					{
					pushFollow(FOLLOW_comp_op_in_comparison1266);
					comp_op();
					state._fsp--;

					pushFollow(FOLLOW_expr_in_comparison1268);
					expr();
					state._fsp--;

					}
					break;

				default :
					break loop55;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "comparison"



	// $ANTLR start "comp_op"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:273:1: comp_op : ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | 'in' | 'not' 'in' | 'is' | 'is' 'not' );
	public final void comp_op() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:273:8: ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | 'in' | 'not' 'in' | 'is' | 'is' 'not' )
			int alt56=11;
			switch ( input.LA(1) ) {
			case LESS:
				{
				alt56=1;
				}
				break;
			case GREATER:
				{
				alt56=2;
				}
				break;
			case EQUAL:
				{
				alt56=3;
				}
				break;
			case GREATEREQUAL:
				{
				alt56=4;
				}
				break;
			case LESSEQUAL:
				{
				alt56=5;
				}
				break;
			case ALT_NOTEQUAL:
				{
				alt56=6;
				}
				break;
			case NOTEQUAL:
				{
				alt56=7;
				}
				break;
			case 81:
				{
				alt56=8;
				}
				break;
			case 84:
				{
				alt56=9;
				}
				break;
			case 82:
				{
				int LA56_10 = input.LA(2);
				if ( (LA56_10==84) ) {
					alt56=11;
				}
				else if ( (LA56_10==BACKQUOTE||LA56_10==COMPLEX||LA56_10==FLOAT||(LA56_10 >= INT && LA56_10 <= LCURLY)||(LA56_10 >= LONGINT && LA56_10 <= MINUS)||LA56_10==NAME||LA56_10==PLUS||(LA56_10 >= STRING && LA56_10 <= TILDE)) ) {
					alt56=10;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 56, 10, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 56, 0, input);
				throw nvae;
			}
			switch (alt56) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:273:10: LESS
					{
					match(input,LESS,FOLLOW_LESS_in_comp_op1279); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:274:3: GREATER
					{
					match(input,GREATER,FOLLOW_GREATER_in_comp_op1283); 
					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:275:3: EQUAL
					{
					match(input,EQUAL,FOLLOW_EQUAL_in_comp_op1287); 
					}
					break;
				case 4 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:276:3: GREATEREQUAL
					{
					match(input,GREATEREQUAL,FOLLOW_GREATEREQUAL_in_comp_op1291); 
					}
					break;
				case 5 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:277:3: LESSEQUAL
					{
					match(input,LESSEQUAL,FOLLOW_LESSEQUAL_in_comp_op1295); 
					}
					break;
				case 6 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:278:3: ALT_NOTEQUAL
					{
					match(input,ALT_NOTEQUAL,FOLLOW_ALT_NOTEQUAL_in_comp_op1299); 
					}
					break;
				case 7 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:279:3: NOTEQUAL
					{
					match(input,NOTEQUAL,FOLLOW_NOTEQUAL_in_comp_op1303); 
					}
					break;
				case 8 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:280:3: 'in'
					{
					match(input,81,FOLLOW_81_in_comp_op1307); 
					}
					break;
				case 9 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:281:3: 'not' 'in'
					{
					match(input,84,FOLLOW_84_in_comp_op1311); 
					match(input,81,FOLLOW_81_in_comp_op1313); 
					}
					break;
				case 10 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:282:3: 'is'
					{
					match(input,82,FOLLOW_82_in_comp_op1317); 
					}
					break;
				case 11 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:283:3: 'is' 'not'
					{
					match(input,82,FOLLOW_82_in_comp_op1321); 
					match(input,84,FOLLOW_84_in_comp_op1323); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "comp_op"



	// $ANTLR start "expr"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:286:1: expr : xor_expr ( VBAR xor_expr )* ;
	public final void expr() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:286:5: ( xor_expr ( VBAR xor_expr )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:286:7: xor_expr ( VBAR xor_expr )*
			{
			pushFollow(FOLLOW_xor_expr_in_expr1332);
			xor_expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:286:16: ( VBAR xor_expr )*
			loop57:
			while (true) {
				int alt57=2;
				int LA57_0 = input.LA(1);
				if ( (LA57_0==VBAR) ) {
					alt57=1;
				}

				switch (alt57) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:286:17: VBAR xor_expr
					{
					match(input,VBAR,FOLLOW_VBAR_in_expr1335); 
					pushFollow(FOLLOW_xor_expr_in_expr1337);
					xor_expr();
					state._fsp--;

					}
					break;

				default :
					break loop57;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "expr"



	// $ANTLR start "xor_expr"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:289:1: xor_expr : and_expr ( CIRCUMFLEX and_expr )* ;
	public final void xor_expr() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:289:9: ( and_expr ( CIRCUMFLEX and_expr )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:289:11: and_expr ( CIRCUMFLEX and_expr )*
			{
			pushFollow(FOLLOW_and_expr_in_xor_expr1348);
			and_expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:289:20: ( CIRCUMFLEX and_expr )*
			loop58:
			while (true) {
				int alt58=2;
				int LA58_0 = input.LA(1);
				if ( (LA58_0==CIRCUMFLEX) ) {
					alt58=1;
				}

				switch (alt58) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:289:21: CIRCUMFLEX and_expr
					{
					match(input,CIRCUMFLEX,FOLLOW_CIRCUMFLEX_in_xor_expr1351); 
					pushFollow(FOLLOW_and_expr_in_xor_expr1353);
					and_expr();
					state._fsp--;

					}
					break;

				default :
					break loop58;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "xor_expr"



	// $ANTLR start "and_expr"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:292:1: and_expr : shift_expr ( AMPER shift_expr )* ;
	public final void and_expr() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:292:9: ( shift_expr ( AMPER shift_expr )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:292:11: shift_expr ( AMPER shift_expr )*
			{
			pushFollow(FOLLOW_shift_expr_in_and_expr1364);
			shift_expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:292:22: ( AMPER shift_expr )*
			loop59:
			while (true) {
				int alt59=2;
				int LA59_0 = input.LA(1);
				if ( (LA59_0==AMPER) ) {
					alt59=1;
				}

				switch (alt59) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:292:23: AMPER shift_expr
					{
					match(input,AMPER,FOLLOW_AMPER_in_and_expr1367); 
					pushFollow(FOLLOW_shift_expr_in_and_expr1369);
					shift_expr();
					state._fsp--;

					}
					break;

				default :
					break loop59;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "and_expr"



	// $ANTLR start "shift_expr"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:295:1: shift_expr : arith_expr ( ( LEFTSHIFT | RIGHTSHIFT ) arith_expr )* ;
	public final void shift_expr() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:295:11: ( arith_expr ( ( LEFTSHIFT | RIGHTSHIFT ) arith_expr )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:295:13: arith_expr ( ( LEFTSHIFT | RIGHTSHIFT ) arith_expr )*
			{
			pushFollow(FOLLOW_arith_expr_in_shift_expr1380);
			arith_expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:295:24: ( ( LEFTSHIFT | RIGHTSHIFT ) arith_expr )*
			loop60:
			while (true) {
				int alt60=2;
				int LA60_0 = input.LA(1);
				if ( (LA60_0==LEFTSHIFT||LA60_0==RIGHTSHIFT) ) {
					alt60=1;
				}

				switch (alt60) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:295:25: ( LEFTSHIFT | RIGHTSHIFT ) arith_expr
					{
					if ( input.LA(1)==LEFTSHIFT||input.LA(1)==RIGHTSHIFT ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_arith_expr_in_shift_expr1389);
					arith_expr();
					state._fsp--;

					}
					break;

				default :
					break loop60;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "shift_expr"



	// $ANTLR start "arith_expr"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:298:1: arith_expr : term ( ( PLUS | MINUS ) term )* ;
	public final void arith_expr() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:298:11: ( term ( ( PLUS | MINUS ) term )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:298:13: term ( ( PLUS | MINUS ) term )*
			{
			pushFollow(FOLLOW_term_in_arith_expr1400);
			term();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:298:18: ( ( PLUS | MINUS ) term )*
			loop61:
			while (true) {
				int alt61=2;
				int LA61_0 = input.LA(1);
				if ( (LA61_0==MINUS||LA61_0==PLUS) ) {
					alt61=1;
				}

				switch (alt61) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:298:19: ( PLUS | MINUS ) term
					{
					if ( input.LA(1)==MINUS||input.LA(1)==PLUS ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_term_in_arith_expr1409);
					term();
					state._fsp--;

					}
					break;

				default :
					break loop61;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "arith_expr"



	// $ANTLR start "term"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:301:1: term : factor ( ( STAR | SLASH | PERCENT | DOUBLESLASH ) factor )* ;
	public final void term() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:301:5: ( factor ( ( STAR | SLASH | PERCENT | DOUBLESLASH ) factor )* )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:301:7: factor ( ( STAR | SLASH | PERCENT | DOUBLESLASH ) factor )*
			{
			pushFollow(FOLLOW_factor_in_term1420);
			factor();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:301:14: ( ( STAR | SLASH | PERCENT | DOUBLESLASH ) factor )*
			loop62:
			while (true) {
				int alt62=2;
				int LA62_0 = input.LA(1);
				if ( (LA62_0==DOUBLESLASH||LA62_0==PERCENT||LA62_0==SLASH||LA62_0==STAR) ) {
					alt62=1;
				}

				switch (alt62) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:301:15: ( STAR | SLASH | PERCENT | DOUBLESLASH ) factor
					{
					if ( input.LA(1)==DOUBLESLASH||input.LA(1)==PERCENT||input.LA(1)==SLASH||input.LA(1)==STAR ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_factor_in_term1440);
					factor();
					state._fsp--;

					}
					break;

				default :
					break loop62;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "term"



	// $ANTLR start "factor"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:304:1: factor : ( ( PLUS | MINUS | TILDE ) factor | power );
	public final void factor() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:305:2: ( ( PLUS | MINUS | TILDE ) factor | power )
			int alt63=2;
			int LA63_0 = input.LA(1);
			if ( (LA63_0==MINUS||LA63_0==PLUS||LA63_0==TILDE) ) {
				alt63=1;
			}
			else if ( (LA63_0==BACKQUOTE||LA63_0==COMPLEX||LA63_0==FLOAT||(LA63_0 >= INT && LA63_0 <= LCURLY)||(LA63_0 >= LONGINT && LA63_0 <= LPAREN)||LA63_0==NAME||LA63_0==STRING) ) {
				alt63=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 63, 0, input);
				throw nvae;
			}

			switch (alt63) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:305:4: ( PLUS | MINUS | TILDE ) factor
					{
					if ( input.LA(1)==MINUS||input.LA(1)==PLUS||input.LA(1)==TILDE ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_factor_in_factor1461);
					factor();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:306:4: power
					{
					pushFollow(FOLLOW_power_in_factor1466);
					power();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "factor"



	// $ANTLR start "power"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:309:1: power : atom ( trailer )* ( options {greedy=true; } : DOUBLESTAR factor )? ;
	public final void power() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:310:2: ( atom ( trailer )* ( options {greedy=true; } : DOUBLESTAR factor )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:310:6: atom ( trailer )* ( options {greedy=true; } : DOUBLESTAR factor )?
			{
			pushFollow(FOLLOW_atom_in_power1479);
			atom();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:310:11: ( trailer )*
			loop64:
			while (true) {
				int alt64=2;
				int LA64_0 = input.LA(1);
				if ( (LA64_0==DOT||LA64_0==LBRACK||LA64_0==LPAREN) ) {
					alt64=1;
				}

				switch (alt64) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:310:12: trailer
					{
					pushFollow(FOLLOW_trailer_in_power1482);
					trailer();
					state._fsp--;

					}
					break;

				default :
					break loop64;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:310:22: ( options {greedy=true; } : DOUBLESTAR factor )?
			int alt65=2;
			int LA65_0 = input.LA(1);
			if ( (LA65_0==DOUBLESTAR) ) {
				alt65=1;
			}
			switch (alt65) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:310:46: DOUBLESTAR factor
					{
					match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_power1494); 
					pushFollow(FOLLOW_factor_in_power1496);
					factor();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "power"



	// $ANTLR start "atom"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:313:1: atom : ( LPAREN ( testlist )? RPAREN | LBRACK ( listmaker )? RBRACK | LCURLY ( dictmaker )? RCURLY | BACKQUOTE testlist BACKQUOTE | NAME | INT | LONGINT | FLOAT | COMPLEX | ( STRING )+ );
	public final void atom() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:313:5: ( LPAREN ( testlist )? RPAREN | LBRACK ( listmaker )? RBRACK | LCURLY ( dictmaker )? RCURLY | BACKQUOTE testlist BACKQUOTE | NAME | INT | LONGINT | FLOAT | COMPLEX | ( STRING )+ )
			int alt70=10;
			switch ( input.LA(1) ) {
			case LPAREN:
				{
				alt70=1;
				}
				break;
			case LBRACK:
				{
				alt70=2;
				}
				break;
			case LCURLY:
				{
				alt70=3;
				}
				break;
			case BACKQUOTE:
				{
				alt70=4;
				}
				break;
			case NAME:
				{
				alt70=5;
				}
				break;
			case INT:
				{
				alt70=6;
				}
				break;
			case LONGINT:
				{
				alt70=7;
				}
				break;
			case FLOAT:
				{
				alt70=8;
				}
				break;
			case COMPLEX:
				{
				alt70=9;
				}
				break;
			case STRING:
				{
				alt70=10;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 70, 0, input);
				throw nvae;
			}
			switch (alt70) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:313:7: LPAREN ( testlist )? RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_atom1507); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:313:14: ( testlist )?
					int alt66=2;
					int LA66_0 = input.LA(1);
					if ( (LA66_0==BACKQUOTE||LA66_0==COMPLEX||LA66_0==FLOAT||(LA66_0 >= INT && LA66_0 <= LCURLY)||(LA66_0 >= LONGINT && LA66_0 <= MINUS)||LA66_0==NAME||LA66_0==PLUS||(LA66_0 >= STRING && LA66_0 <= TILDE)||(LA66_0 >= 83 && LA66_0 <= 84)) ) {
						alt66=1;
					}
					switch (alt66) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:313:15: testlist
							{
							pushFollow(FOLLOW_testlist_in_atom1510);
							testlist();
							state._fsp--;

							}
							break;

					}

					match(input,RPAREN,FOLLOW_RPAREN_in_atom1514); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:314:4: LBRACK ( listmaker )? RBRACK
					{
					match(input,LBRACK,FOLLOW_LBRACK_in_atom1519); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:314:11: ( listmaker )?
					int alt67=2;
					int LA67_0 = input.LA(1);
					if ( (LA67_0==BACKQUOTE||LA67_0==COMPLEX||LA67_0==FLOAT||(LA67_0 >= INT && LA67_0 <= LCURLY)||(LA67_0 >= LONGINT && LA67_0 <= MINUS)||LA67_0==NAME||LA67_0==PLUS||(LA67_0 >= STRING && LA67_0 <= TILDE)||(LA67_0 >= 83 && LA67_0 <= 84)) ) {
						alt67=1;
					}
					switch (alt67) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:314:12: listmaker
							{
							pushFollow(FOLLOW_listmaker_in_atom1522);
							listmaker();
							state._fsp--;

							}
							break;

					}

					match(input,RBRACK,FOLLOW_RBRACK_in_atom1526); 
					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:315:4: LCURLY ( dictmaker )? RCURLY
					{
					match(input,LCURLY,FOLLOW_LCURLY_in_atom1531); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:315:11: ( dictmaker )?
					int alt68=2;
					int LA68_0 = input.LA(1);
					if ( (LA68_0==BACKQUOTE||LA68_0==COMPLEX||LA68_0==FLOAT||(LA68_0 >= INT && LA68_0 <= LCURLY)||(LA68_0 >= LONGINT && LA68_0 <= MINUS)||LA68_0==NAME||LA68_0==PLUS||(LA68_0 >= STRING && LA68_0 <= TILDE)||(LA68_0 >= 83 && LA68_0 <= 84)) ) {
						alt68=1;
					}
					switch (alt68) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:315:12: dictmaker
							{
							pushFollow(FOLLOW_dictmaker_in_atom1534);
							dictmaker();
							state._fsp--;

							}
							break;

					}

					match(input,RCURLY,FOLLOW_RCURLY_in_atom1538); 
					}
					break;
				case 4 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:316:4: BACKQUOTE testlist BACKQUOTE
					{
					match(input,BACKQUOTE,FOLLOW_BACKQUOTE_in_atom1543); 
					pushFollow(FOLLOW_testlist_in_atom1545);
					testlist();
					state._fsp--;

					match(input,BACKQUOTE,FOLLOW_BACKQUOTE_in_atom1547); 
					}
					break;
				case 5 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:317:4: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_atom1552); 
					}
					break;
				case 6 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:318:4: INT
					{
					match(input,INT,FOLLOW_INT_in_atom1557); 
					}
					break;
				case 7 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:319:7: LONGINT
					{
					match(input,LONGINT,FOLLOW_LONGINT_in_atom1565); 
					}
					break;
				case 8 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:320:7: FLOAT
					{
					match(input,FLOAT,FOLLOW_FLOAT_in_atom1573); 
					}
					break;
				case 9 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:321:7: COMPLEX
					{
					match(input,COMPLEX,FOLLOW_COMPLEX_in_atom1581); 
					}
					break;
				case 10 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:322:4: ( STRING )+
					{
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:322:4: ( STRING )+
					int cnt69=0;
					loop69:
					while (true) {
						int alt69=2;
						int LA69_0 = input.LA(1);
						if ( (LA69_0==STRING) ) {
							alt69=1;
						}

						switch (alt69) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:322:5: STRING
							{
							match(input,STRING,FOLLOW_STRING_in_atom1587); 
							}
							break;

						default :
							if ( cnt69 >= 1 ) break loop69;
							EarlyExitException eee = new EarlyExitException(69, input);
							throw eee;
						}
						cnt69++;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "atom"



	// $ANTLR start "listmaker"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:1: listmaker : test ( list_for | ( options {greedy=true; } : COMMA test )* ) ( COMMA )? ;
	public final void listmaker() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:10: ( test ( list_for | ( options {greedy=true; } : COMMA test )* ) ( COMMA )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:12: test ( list_for | ( options {greedy=true; } : COMMA test )* ) ( COMMA )?
			{
			pushFollow(FOLLOW_test_in_listmaker1598);
			test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:17: ( list_for | ( options {greedy=true; } : COMMA test )* )
			int alt72=2;
			int LA72_0 = input.LA(1);
			if ( (LA72_0==76) ) {
				alt72=1;
			}
			else if ( (LA72_0==COMMA||LA72_0==RBRACK) ) {
				alt72=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 72, 0, input);
				throw nvae;
			}

			switch (alt72) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:19: list_for
					{
					pushFollow(FOLLOW_list_for_in_listmaker1602);
					list_for();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:30: ( options {greedy=true; } : COMMA test )*
					{
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:30: ( options {greedy=true; } : COMMA test )*
					loop71:
					while (true) {
						int alt71=2;
						int LA71_0 = input.LA(1);
						if ( (LA71_0==COMMA) ) {
							int LA71_1 = input.LA(2);
							if ( (LA71_1==BACKQUOTE||LA71_1==COMPLEX||LA71_1==FLOAT||(LA71_1 >= INT && LA71_1 <= LCURLY)||(LA71_1 >= LONGINT && LA71_1 <= MINUS)||LA71_1==NAME||LA71_1==PLUS||(LA71_1 >= STRING && LA71_1 <= TILDE)||(LA71_1 >= 83 && LA71_1 <= 84)) ) {
								alt71=1;
							}

						}

						switch (alt71) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:54: COMMA test
							{
							match(input,COMMA,FOLLOW_COMMA_in_listmaker1614); 
							pushFollow(FOLLOW_test_in_listmaker1616);
							test();
							state._fsp--;

							}
							break;

						default :
							break loop71;
						}
					}

					}
					break;

			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:69: ( COMMA )?
			int alt73=2;
			int LA73_0 = input.LA(1);
			if ( (LA73_0==COMMA) ) {
				alt73=1;
			}
			switch (alt73) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:325:70: COMMA
					{
					match(input,COMMA,FOLLOW_COMMA_in_listmaker1623); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "listmaker"



	// $ANTLR start "lambdef"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:328:1: lambdef : 'lambda' ( varargslist )? COLON test ;
	public final void lambdef() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:328:8: ( 'lambda' ( varargslist )? COLON test )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:328:10: 'lambda' ( varargslist )? COLON test
			{
			match(input,83,FOLLOW_83_in_lambdef1634); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:328:19: ( varargslist )?
			int alt74=2;
			int LA74_0 = input.LA(1);
			if ( (LA74_0==DOUBLESTAR||LA74_0==LPAREN||LA74_0==NAME||LA74_0==STAR) ) {
				alt74=1;
			}
			switch (alt74) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:328:20: varargslist
					{
					pushFollow(FOLLOW_varargslist_in_lambdef1637);
					varargslist();
					state._fsp--;

					}
					break;

			}

			match(input,COLON,FOLLOW_COLON_in_lambdef1641); 
			pushFollow(FOLLOW_test_in_lambdef1643);
			test();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "lambdef"



	// $ANTLR start "trailer"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:331:1: trailer : ( LPAREN ( arglist )? RPAREN | LBRACK subscriptlist RBRACK | DOT NAME );
	public final void trailer() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:331:8: ( LPAREN ( arglist )? RPAREN | LBRACK subscriptlist RBRACK | DOT NAME )
			int alt76=3;
			switch ( input.LA(1) ) {
			case LPAREN:
				{
				alt76=1;
				}
				break;
			case LBRACK:
				{
				alt76=2;
				}
				break;
			case DOT:
				{
				alt76=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 76, 0, input);
				throw nvae;
			}
			switch (alt76) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:331:10: LPAREN ( arglist )? RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_trailer1652); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:331:17: ( arglist )?
					int alt75=2;
					int LA75_0 = input.LA(1);
					if ( (LA75_0==BACKQUOTE||LA75_0==COMPLEX||LA75_0==DOUBLESTAR||LA75_0==FLOAT||(LA75_0 >= INT && LA75_0 <= LCURLY)||(LA75_0 >= LONGINT && LA75_0 <= MINUS)||LA75_0==NAME||LA75_0==PLUS||LA75_0==STAR||(LA75_0 >= STRING && LA75_0 <= TILDE)||(LA75_0 >= 83 && LA75_0 <= 84)) ) {
						alt75=1;
					}
					switch (alt75) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:331:18: arglist
							{
							pushFollow(FOLLOW_arglist_in_trailer1655);
							arglist();
							state._fsp--;

							}
							break;

					}

					match(input,RPAREN,FOLLOW_RPAREN_in_trailer1659); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:332:4: LBRACK subscriptlist RBRACK
					{
					match(input,LBRACK,FOLLOW_LBRACK_in_trailer1664); 
					pushFollow(FOLLOW_subscriptlist_in_trailer1666);
					subscriptlist();
					state._fsp--;

					match(input,RBRACK,FOLLOW_RBRACK_in_trailer1668); 
					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:333:4: DOT NAME
					{
					match(input,DOT,FOLLOW_DOT_in_trailer1673); 
					match(input,NAME,FOLLOW_NAME_in_trailer1675); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "trailer"



	// $ANTLR start "subscriptlist"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:336:1: subscriptlist : subscript ( options {greedy=true; } : COMMA subscript )* ( COMMA )? ;
	public final void subscriptlist() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:337:5: ( subscript ( options {greedy=true; } : COMMA subscript )* ( COMMA )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:337:9: subscript ( options {greedy=true; } : COMMA subscript )* ( COMMA )?
			{
			pushFollow(FOLLOW_subscript_in_subscriptlist1691);
			subscript();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:337:19: ( options {greedy=true; } : COMMA subscript )*
			loop77:
			while (true) {
				int alt77=2;
				int LA77_0 = input.LA(1);
				if ( (LA77_0==COMMA) ) {
					int LA77_1 = input.LA(2);
					if ( (LA77_1==BACKQUOTE||LA77_1==COLON||LA77_1==COMPLEX||LA77_1==DOT||LA77_1==FLOAT||(LA77_1 >= INT && LA77_1 <= LCURLY)||(LA77_1 >= LONGINT && LA77_1 <= MINUS)||LA77_1==NAME||LA77_1==PLUS||(LA77_1 >= STRING && LA77_1 <= TILDE)||(LA77_1 >= 83 && LA77_1 <= 84)) ) {
						alt77=1;
					}

				}

				switch (alt77) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:337:43: COMMA subscript
					{
					match(input,COMMA,FOLLOW_COMMA_in_subscriptlist1701); 
					pushFollow(FOLLOW_subscript_in_subscriptlist1703);
					subscript();
					state._fsp--;

					}
					break;

				default :
					break loop77;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:337:61: ( COMMA )?
			int alt78=2;
			int LA78_0 = input.LA(1);
			if ( (LA78_0==COMMA) ) {
				alt78=1;
			}
			switch (alt78) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:337:62: COMMA
					{
					match(input,COMMA,FOLLOW_COMMA_in_subscriptlist1708); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "subscriptlist"



	// $ANTLR start "subscript"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:340:1: subscript : ( DOT DOT DOT | test ( COLON ( test )? ( sliceop )? )? | COLON ( test )? ( sliceop )? );
	public final void subscript() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:341:2: ( DOT DOT DOT | test ( COLON ( test )? ( sliceop )? )? | COLON ( test )? ( sliceop )? )
			int alt84=3;
			switch ( input.LA(1) ) {
			case DOT:
				{
				alt84=1;
				}
				break;
			case BACKQUOTE:
			case COMPLEX:
			case FLOAT:
			case INT:
			case LBRACK:
			case LCURLY:
			case LONGINT:
			case LPAREN:
			case MINUS:
			case NAME:
			case PLUS:
			case STRING:
			case TILDE:
			case 83:
			case 84:
				{
				alt84=2;
				}
				break;
			case COLON:
				{
				alt84=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 84, 0, input);
				throw nvae;
			}
			switch (alt84) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:341:4: DOT DOT DOT
					{
					match(input,DOT,FOLLOW_DOT_in_subscript1721); 
					match(input,DOT,FOLLOW_DOT_in_subscript1723); 
					match(input,DOT,FOLLOW_DOT_in_subscript1725); 
					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:7: test ( COLON ( test )? ( sliceop )? )?
					{
					pushFollow(FOLLOW_test_in_subscript1733);
					test();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:12: ( COLON ( test )? ( sliceop )? )?
					int alt81=2;
					int LA81_0 = input.LA(1);
					if ( (LA81_0==COLON) ) {
						alt81=1;
					}
					switch (alt81) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:13: COLON ( test )? ( sliceop )?
							{
							match(input,COLON,FOLLOW_COLON_in_subscript1736); 
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:19: ( test )?
							int alt79=2;
							int LA79_0 = input.LA(1);
							if ( (LA79_0==BACKQUOTE||LA79_0==COMPLEX||LA79_0==FLOAT||(LA79_0 >= INT && LA79_0 <= LCURLY)||(LA79_0 >= LONGINT && LA79_0 <= MINUS)||LA79_0==NAME||LA79_0==PLUS||(LA79_0 >= STRING && LA79_0 <= TILDE)||(LA79_0 >= 83 && LA79_0 <= 84)) ) {
								alt79=1;
							}
							switch (alt79) {
								case 1 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:20: test
									{
									pushFollow(FOLLOW_test_in_subscript1739);
									test();
									state._fsp--;

									}
									break;

							}

							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:27: ( sliceop )?
							int alt80=2;
							int LA80_0 = input.LA(1);
							if ( (LA80_0==COLON) ) {
								alt80=1;
							}
							switch (alt80) {
								case 1 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:342:28: sliceop
									{
									pushFollow(FOLLOW_sliceop_in_subscript1744);
									sliceop();
									state._fsp--;

									}
									break;

							}

							}
							break;

					}

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:343:7: COLON ( test )? ( sliceop )?
					{
					match(input,COLON,FOLLOW_COLON_in_subscript1756); 
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:343:13: ( test )?
					int alt82=2;
					int LA82_0 = input.LA(1);
					if ( (LA82_0==BACKQUOTE||LA82_0==COMPLEX||LA82_0==FLOAT||(LA82_0 >= INT && LA82_0 <= LCURLY)||(LA82_0 >= LONGINT && LA82_0 <= MINUS)||LA82_0==NAME||LA82_0==PLUS||(LA82_0 >= STRING && LA82_0 <= TILDE)||(LA82_0 >= 83 && LA82_0 <= 84)) ) {
						alt82=1;
					}
					switch (alt82) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:343:14: test
							{
							pushFollow(FOLLOW_test_in_subscript1759);
							test();
							state._fsp--;

							}
							break;

					}

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:343:21: ( sliceop )?
					int alt83=2;
					int LA83_0 = input.LA(1);
					if ( (LA83_0==COLON) ) {
						alt83=1;
					}
					switch (alt83) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:343:22: sliceop
							{
							pushFollow(FOLLOW_sliceop_in_subscript1764);
							sliceop();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "subscript"



	// $ANTLR start "sliceop"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:346:1: sliceop : COLON ( test )? ;
	public final void sliceop() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:346:8: ( COLON ( test )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:346:10: COLON ( test )?
			{
			match(input,COLON,FOLLOW_COLON_in_sliceop1778); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:346:16: ( test )?
			int alt85=2;
			int LA85_0 = input.LA(1);
			if ( (LA85_0==BACKQUOTE||LA85_0==COMPLEX||LA85_0==FLOAT||(LA85_0 >= INT && LA85_0 <= LCURLY)||(LA85_0 >= LONGINT && LA85_0 <= MINUS)||LA85_0==NAME||LA85_0==PLUS||(LA85_0 >= STRING && LA85_0 <= TILDE)||(LA85_0 >= 83 && LA85_0 <= 84)) ) {
				alt85=1;
			}
			switch (alt85) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:346:17: test
					{
					pushFollow(FOLLOW_test_in_sliceop1781);
					test();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "sliceop"



	// $ANTLR start "exprlist"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:349:1: exprlist : expr ( options {k=2; } : COMMA expr )* ( COMMA )? ;
	public final void exprlist() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:350:5: ( expr ( options {k=2; } : COMMA expr )* ( COMMA )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:350:9: expr ( options {k=2; } : COMMA expr )* ( COMMA )?
			{
			pushFollow(FOLLOW_expr_in_exprlist1799);
			expr();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:350:14: ( options {k=2; } : COMMA expr )*
			loop86:
			while (true) {
				int alt86=2;
				int LA86_0 = input.LA(1);
				if ( (LA86_0==COMMA) ) {
					int LA86_1 = input.LA(2);
					if ( (LA86_1==BACKQUOTE||LA86_1==COMPLEX||LA86_1==FLOAT||(LA86_1 >= INT && LA86_1 <= LCURLY)||(LA86_1 >= LONGINT && LA86_1 <= MINUS)||LA86_1==NAME||LA86_1==PLUS||(LA86_1 >= STRING && LA86_1 <= TILDE)) ) {
						alt86=1;
					}

				}

				switch (alt86) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:350:30: COMMA expr
					{
					match(input,COMMA,FOLLOW_COMMA_in_exprlist1809); 
					pushFollow(FOLLOW_expr_in_exprlist1811);
					expr();
					state._fsp--;

					}
					break;

				default :
					break loop86;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:350:43: ( COMMA )?
			int alt87=2;
			int LA87_0 = input.LA(1);
			if ( (LA87_0==COMMA) ) {
				alt87=1;
			}
			switch (alt87) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:350:44: COMMA
					{
					match(input,COMMA,FOLLOW_COMMA_in_exprlist1816); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exprlist"



	// $ANTLR start "testlist"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:353:1: testlist : test ( options {k=2; } : COMMA test )* ( COMMA )? ;
	public final void testlist() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:354:5: ( test ( options {k=2; } : COMMA test )* ( COMMA )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:354:9: test ( options {k=2; } : COMMA test )* ( COMMA )?
			{
			pushFollow(FOLLOW_test_in_testlist1834);
			test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:354:14: ( options {k=2; } : COMMA test )*
			loop88:
			while (true) {
				int alt88=2;
				int LA88_0 = input.LA(1);
				if ( (LA88_0==COMMA) ) {
					int LA88_1 = input.LA(2);
					if ( (LA88_1==BACKQUOTE) ) {
						alt88=1;
					}
					else if ( (LA88_1==COMPLEX||LA88_1==FLOAT||(LA88_1 >= INT && LA88_1 <= LCURLY)||(LA88_1 >= LONGINT && LA88_1 <= MINUS)||LA88_1==NAME||LA88_1==PLUS||(LA88_1 >= STRING && LA88_1 <= TILDE)||(LA88_1 >= 83 && LA88_1 <= 84)) ) {
						alt88=1;
					}

				}

				switch (alt88) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:354:31: COMMA test
					{
					match(input,COMMA,FOLLOW_COMMA_in_testlist1845); 
					pushFollow(FOLLOW_test_in_testlist1847);
					test();
					state._fsp--;

					}
					break;

				default :
					break loop88;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:354:44: ( COMMA )?
			int alt89=2;
			int LA89_0 = input.LA(1);
			if ( (LA89_0==COMMA) ) {
				alt89=1;
			}
			switch (alt89) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:354:45: COMMA
					{
					match(input,COMMA,FOLLOW_COMMA_in_testlist1852); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "testlist"



	// $ANTLR start "dictmaker"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:357:1: dictmaker : test COLON test ( options {k=2; } : COMMA test COLON test )* ( COMMA )? ;
	public final void dictmaker() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:358:5: ( test COLON test ( options {k=2; } : COMMA test COLON test )* ( COMMA )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:358:9: test COLON test ( options {k=2; } : COMMA test COLON test )* ( COMMA )?
			{
			pushFollow(FOLLOW_test_in_dictmaker1873);
			test();
			state._fsp--;

			match(input,COLON,FOLLOW_COLON_in_dictmaker1875); 
			pushFollow(FOLLOW_test_in_dictmaker1877);
			test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:359:9: ( options {k=2; } : COMMA test COLON test )*
			loop90:
			while (true) {
				int alt90=2;
				int LA90_0 = input.LA(1);
				if ( (LA90_0==COMMA) ) {
					int LA90_1 = input.LA(2);
					if ( (LA90_1==BACKQUOTE||LA90_1==COMPLEX||LA90_1==FLOAT||(LA90_1 >= INT && LA90_1 <= LCURLY)||(LA90_1 >= LONGINT && LA90_1 <= MINUS)||LA90_1==NAME||LA90_1==PLUS||(LA90_1 >= STRING && LA90_1 <= TILDE)||(LA90_1 >= 83 && LA90_1 <= 84)) ) {
						alt90=1;
					}

				}

				switch (alt90) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:359:25: COMMA test COLON test
					{
					match(input,COMMA,FOLLOW_COMMA_in_dictmaker1895); 
					pushFollow(FOLLOW_test_in_dictmaker1897);
					test();
					state._fsp--;

					match(input,COLON,FOLLOW_COLON_in_dictmaker1899); 
					pushFollow(FOLLOW_test_in_dictmaker1901);
					test();
					state._fsp--;

					}
					break;

				default :
					break loop90;
				}
			}

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:359:49: ( COMMA )?
			int alt91=2;
			int LA91_0 = input.LA(1);
			if ( (LA91_0==COMMA) ) {
				alt91=1;
			}
			switch (alt91) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:359:50: COMMA
					{
					match(input,COMMA,FOLLOW_COMMA_in_dictmaker1906); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "dictmaker"



	// $ANTLR start "classdef"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:362:1: classdef : 'class' NAME ( LPAREN testlist RPAREN )? COLON suite ;
	public final void classdef() throws RecognitionException {
		Token NAME2=null;

		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:362:9: ( 'class' NAME ( LPAREN testlist RPAREN )? COLON suite )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:362:11: 'class' NAME ( LPAREN testlist RPAREN )? COLON suite
			{
			match(input,67,FOLLOW_67_in_classdef1920); 
			NAME2=(Token)match(input,NAME,FOLLOW_NAME_in_classdef1922); 
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:362:24: ( LPAREN testlist RPAREN )?
			int alt92=2;
			int LA92_0 = input.LA(1);
			if ( (LA92_0==LPAREN) ) {
				alt92=1;
			}
			switch (alt92) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:362:25: LPAREN testlist RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_classdef1925); 
					pushFollow(FOLLOW_testlist_in_classdef1927);
					testlist();
					state._fsp--;

					match(input,RPAREN,FOLLOW_RPAREN_in_classdef1929); 
					}
					break;

			}

			match(input,COLON,FOLLOW_COLON_in_classdef1933); 
			pushFollow(FOLLOW_suite_in_classdef1935);
			suite();
			state._fsp--;

			System.out.println("found class def "+(NAME2!=null?NAME2.getText():null));
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "classdef"



	// $ANTLR start "arglist"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:366:1: arglist : ( argument ( COMMA argument )* ( COMMA ( STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )? )? | STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test );
	public final void arglist() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:366:8: ( argument ( COMMA argument )* ( COMMA ( STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )? )? | STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )
			int alt98=3;
			switch ( input.LA(1) ) {
			case BACKQUOTE:
			case COMPLEX:
			case FLOAT:
			case INT:
			case LBRACK:
			case LCURLY:
			case LONGINT:
			case LPAREN:
			case MINUS:
			case NAME:
			case PLUS:
			case STRING:
			case TILDE:
			case 83:
			case 84:
				{
				alt98=1;
				}
				break;
			case STAR:
				{
				alt98=2;
				}
				break;
			case DOUBLESTAR:
				{
				alt98=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 98, 0, input);
				throw nvae;
			}
			switch (alt98) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:366:10: argument ( COMMA argument )* ( COMMA ( STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )? )?
					{
					pushFollow(FOLLOW_argument_in_arglist1947);
					argument();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:366:19: ( COMMA argument )*
					loop93:
					while (true) {
						int alt93=2;
						int LA93_0 = input.LA(1);
						if ( (LA93_0==COMMA) ) {
							int LA93_1 = input.LA(2);
							if ( (LA93_1==BACKQUOTE||LA93_1==COMPLEX||LA93_1==FLOAT||(LA93_1 >= INT && LA93_1 <= LCURLY)||(LA93_1 >= LONGINT && LA93_1 <= MINUS)||LA93_1==NAME||LA93_1==PLUS||(LA93_1 >= STRING && LA93_1 <= TILDE)||(LA93_1 >= 83 && LA93_1 <= 84)) ) {
								alt93=1;
							}

						}

						switch (alt93) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:366:20: COMMA argument
							{
							match(input,COMMA,FOLLOW_COMMA_in_arglist1950); 
							pushFollow(FOLLOW_argument_in_arglist1952);
							argument();
							state._fsp--;

							}
							break;

						default :
							break loop93;
						}
					}

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:367:9: ( COMMA ( STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )? )?
					int alt96=2;
					int LA96_0 = input.LA(1);
					if ( (LA96_0==COMMA) ) {
						alt96=1;
					}
					switch (alt96) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:367:11: COMMA ( STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )?
							{
							match(input,COMMA,FOLLOW_COMMA_in_arglist1966); 
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:368:11: ( STAR test ( COMMA DOUBLESTAR test )? | DOUBLESTAR test )?
							int alt95=3;
							int LA95_0 = input.LA(1);
							if ( (LA95_0==STAR) ) {
								alt95=1;
							}
							else if ( (LA95_0==DOUBLESTAR) ) {
								alt95=2;
							}
							switch (alt95) {
								case 1 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:368:13: STAR test ( COMMA DOUBLESTAR test )?
									{
									match(input,STAR,FOLLOW_STAR_in_arglist1980); 
									pushFollow(FOLLOW_test_in_arglist1982);
									test();
									state._fsp--;

									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:368:23: ( COMMA DOUBLESTAR test )?
									int alt94=2;
									int LA94_0 = input.LA(1);
									if ( (LA94_0==COMMA) ) {
										alt94=1;
									}
									switch (alt94) {
										case 1 :
											// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:368:24: COMMA DOUBLESTAR test
											{
											match(input,COMMA,FOLLOW_COMMA_in_arglist1985); 
											match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist1987); 
											pushFollow(FOLLOW_test_in_arglist1989);
											test();
											state._fsp--;

											}
											break;

									}

									}
									break;
								case 2 :
									// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:369:13: DOUBLESTAR test
									{
									match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist2005); 
									pushFollow(FOLLOW_test_in_arglist2007);
									test();
									state._fsp--;

									}
									break;

							}

							}
							break;

					}

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:372:9: STAR test ( COMMA DOUBLESTAR test )?
					{
					match(input,STAR,FOLLOW_STAR_in_arglist2041); 
					pushFollow(FOLLOW_test_in_arglist2043);
					test();
					state._fsp--;

					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:372:19: ( COMMA DOUBLESTAR test )?
					int alt97=2;
					int LA97_0 = input.LA(1);
					if ( (LA97_0==COMMA) ) {
						alt97=1;
					}
					switch (alt97) {
						case 1 :
							// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:372:20: COMMA DOUBLESTAR test
							{
							match(input,COMMA,FOLLOW_COMMA_in_arglist2046); 
							match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist2048); 
							pushFollow(FOLLOW_test_in_arglist2050);
							test();
							state._fsp--;

							}
							break;

					}

					}
					break;
				case 3 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:373:9: DOUBLESTAR test
					{
					match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist2062); 
					pushFollow(FOLLOW_test_in_arglist2064);
					test();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "arglist"



	// $ANTLR start "argument"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:376:1: argument : test ( ASSIGN test )? ;
	public final void argument() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:376:10: ( test ( ASSIGN test )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:376:12: test ( ASSIGN test )?
			{
			pushFollow(FOLLOW_test_in_argument2077);
			test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:376:17: ( ASSIGN test )?
			int alt99=2;
			int LA99_0 = input.LA(1);
			if ( (LA99_0==ASSIGN) ) {
				alt99=1;
			}
			switch (alt99) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:376:18: ASSIGN test
					{
					match(input,ASSIGN,FOLLOW_ASSIGN_in_argument2080); 
					pushFollow(FOLLOW_test_in_argument2082);
					test();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "argument"



	// $ANTLR start "list_iter"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:379:1: list_iter : ( list_for | list_if );
	public final void list_iter() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:379:10: ( list_for | list_if )
			int alt100=2;
			int LA100_0 = input.LA(1);
			if ( (LA100_0==76) ) {
				alt100=1;
			}
			else if ( (LA100_0==79) ) {
				alt100=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 100, 0, input);
				throw nvae;
			}

			switch (alt100) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:379:12: list_for
					{
					pushFollow(FOLLOW_list_for_in_list_iter2101);
					list_for();
					state._fsp--;

					}
					break;
				case 2 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:380:4: list_if
					{
					pushFollow(FOLLOW_list_if_in_list_iter2106);
					list_if();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "list_iter"



	// $ANTLR start "list_for"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:383:1: list_for : 'for' exprlist 'in' testlist ( list_iter )? ;
	public final void list_for() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:383:9: ( 'for' exprlist 'in' testlist ( list_iter )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:383:11: 'for' exprlist 'in' testlist ( list_iter )?
			{
			match(input,76,FOLLOW_76_in_list_for2115); 
			pushFollow(FOLLOW_exprlist_in_list_for2117);
			exprlist();
			state._fsp--;

			match(input,81,FOLLOW_81_in_list_for2119); 
			pushFollow(FOLLOW_testlist_in_list_for2121);
			testlist();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:383:40: ( list_iter )?
			int alt101=2;
			int LA101_0 = input.LA(1);
			if ( (LA101_0==76||LA101_0==79) ) {
				alt101=1;
			}
			switch (alt101) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:383:41: list_iter
					{
					pushFollow(FOLLOW_list_iter_in_list_for2124);
					list_iter();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "list_for"



	// $ANTLR start "list_if"
	// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:386:1: list_if : 'if' test ( list_iter )? ;
	public final void list_if() throws RecognitionException {
		try {
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:386:8: ( 'if' test ( list_iter )? )
			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:386:10: 'if' test ( list_iter )?
			{
			match(input,79,FOLLOW_79_in_list_if2135); 
			pushFollow(FOLLOW_test_in_list_if2137);
			test();
			state._fsp--;

			// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:386:20: ( list_iter )?
			int alt102=2;
			int LA102_0 = input.LA(1);
			if ( (LA102_0==76||LA102_0==79) ) {
				alt102=1;
			}
			switch (alt102) {
				case 1 :
					// D:\\DEV\\Java\\Project Structure and Compiler\\ANTLR\\Test\\PythonJava\\Python.g:386:21: list_iter
					{
					pushFollow(FOLLOW_list_iter_in_list_if2140);
					list_iter();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "list_if"

	// Delegated rules



	public static final BitSet FOLLOW_NEWLINE_in_single_input47 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_simple_stmt_in_single_input52 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_compound_stmt_in_single_input57 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_NEWLINE_in_single_input59 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_file_input76 = new BitSet(new long[]{0x18008DC1C4004102L,0x000000001FD9F47EL});
	public static final BitSet FOLLOW_stmt_in_file_input80 = new BitSet(new long[]{0x18008DC1C4004102L,0x000000001FD9F47EL});
	public static final BitSet FOLLOW_NEWLINE_in_eval_input99 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_eval_input103 = new BitSet(new long[]{0x0000080000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_eval_input106 = new BitSet(new long[]{0x0000080000000002L});
	public static final BitSet FOLLOW_69_in_funcdef124 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_funcdef126 = new BitSet(new long[]{0x0000008000000000L});
	public static final BitSet FOLLOW_parameters_in_funcdef128 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_funcdef130 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_funcdef132 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_parameters151 = new BitSet(new long[]{0x0220048000200000L});
	public static final BitSet FOLLOW_varargslist_in_parameters154 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_RPAREN_in_parameters158 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_defparameter_in_varargslist174 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_varargslist184 = new BitSet(new long[]{0x0000048000000000L});
	public static final BitSet FOLLOW_defparameter_in_varargslist186 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_varargslist199 = new BitSet(new long[]{0x0200000000200002L});
	public static final BitSet FOLLOW_STAR_in_varargslist215 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_varargslist217 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_varargslist220 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist222 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_varargslist224 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist242 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_varargslist244 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_varargslist280 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_varargslist282 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_varargslist285 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist287 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_varargslist289 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist301 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_varargslist303 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_fpdef_in_defparameter322 = new BitSet(new long[]{0x0000000000000082L});
	public static final BitSet FOLLOW_ASSIGN_in_defparameter325 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_defparameter327 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_fpdef348 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_fpdef355 = new BitSet(new long[]{0x0000048000000000L});
	public static final BitSet FOLLOW_fplist_in_fpdef357 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_RPAREN_in_fpdef359 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_fpdef_in_fplist375 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_fplist385 = new BitSet(new long[]{0x0000048000000000L});
	public static final BitSet FOLLOW_fpdef_in_fplist387 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_fplist392 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_simple_stmt_in_stmt404 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_compound_stmt_in_stmt409 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_small_stmt_in_simple_stmt425 = new BitSet(new long[]{0x0040080000000000L});
	public static final BitSet FOLLOW_SEMI_in_simple_stmt435 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_small_stmt_in_simple_stmt437 = new BitSet(new long[]{0x0040080000000000L});
	public static final BitSet FOLLOW_SEMI_in_simple_stmt442 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_NEWLINE_in_simple_stmt446 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_stmt_in_small_stmt455 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_print_stmt_in_small_stmt460 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_del_stmt_in_small_stmt465 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pass_stmt_in_small_stmt470 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_flow_stmt_in_small_stmt475 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_import_stmt_in_small_stmt480 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_global_stmt_in_small_stmt485 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_exec_stmt_in_small_stmt490 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_assert_stmt_in_small_stmt495 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_testlist_in_expr_stmt506 = new BitSet(new long[]{0x45114208005004C2L});
	public static final BitSet FOLLOW_augassign_in_expr_stmt512 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_expr_stmt514 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ASSIGN_in_expr_stmt521 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_expr_stmt523 = new BitSet(new long[]{0x0000000000000082L});
	public static final BitSet FOLLOW_87_in_print_stmt616 = new BitSet(new long[]{0x180885C1C4004102L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_print_stmt630 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_RIGHTSHIFT_in_print_stmt644 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_print_stmt646 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_70_in_del_stmt666 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_exprlist_in_del_stmt668 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_86_in_pass_stmt677 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_break_stmt_in_flow_stmt686 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_continue_stmt_in_flow_stmt691 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_return_stmt_in_flow_stmt696 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_raise_stmt_in_flow_stmt701 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_yield_stmt_in_flow_stmt706 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_66_in_break_stmt715 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_68_in_continue_stmt724 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_89_in_return_stmt733 = new BitSet(new long[]{0x180085C1C4004102L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_return_stmt736 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_92_in_yield_stmt747 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_yield_stmt749 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_88_in_raise_stmt758 = new BitSet(new long[]{0x180085C1C4004102L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_raise_stmt761 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_raise_stmt764 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_raise_stmt766 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_raise_stmt769 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_raise_stmt771 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_80_in_import_stmt793 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_dotted_as_name_in_import_stmt795 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_import_stmt798 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_dotted_as_name_in_import_stmt800 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_77_in_import_stmt809 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_dotted_name_in_import_stmt811 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
	public static final BitSet FOLLOW_80_in_import_stmt813 = new BitSet(new long[]{0x0200040000000000L});
	public static final BitSet FOLLOW_STAR_in_import_stmt824 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_import_as_name_in_import_stmt828 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_import_stmt831 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_import_as_name_in_import_stmt833 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_NAME_in_import_as_name852 = new BitSet(new long[]{0x0000040000000002L});
	public static final BitSet FOLLOW_NAME_in_import_as_name855 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_import_as_name857 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dotted_name_in_dotted_as_name868 = new BitSet(new long[]{0x0000040000000002L});
	public static final BitSet FOLLOW_NAME_in_dotted_as_name871 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_dotted_as_name873 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_dotted_name884 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_DOT_in_dotted_name887 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_dotted_name889 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_78_in_global_stmt900 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_global_stmt902 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_global_stmt905 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_global_stmt907 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_74_in_exec_stmt918 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_expr_in_exec_stmt920 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
	public static final BitSet FOLLOW_81_in_exec_stmt923 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_exec_stmt925 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_exec_stmt928 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_exec_stmt930 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_65_in_assert_stmt943 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_assert_stmt945 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_assert_stmt948 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_assert_stmt950 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_if_stmt_in_compound_stmt962 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_while_stmt_in_compound_stmt967 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_for_stmt_in_compound_stmt972 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_try_stmt_in_compound_stmt977 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_funcdef_in_compound_stmt982 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_classdef_in_compound_stmt987 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_if_stmt996 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_if_stmt998 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_if_stmt1000 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_if_stmt1002 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000180L});
	public static final BitSet FOLLOW_71_in_if_stmt1005 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_if_stmt1007 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_if_stmt1009 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_if_stmt1011 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000180L});
	public static final BitSet FOLLOW_72_in_if_stmt1016 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_if_stmt1018 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_if_stmt1020 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_91_in_while_stmt1031 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_while_stmt1033 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_while_stmt1035 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_while_stmt1037 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
	public static final BitSet FOLLOW_72_in_while_stmt1040 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_while_stmt1042 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_while_stmt1044 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_76_in_for_stmt1055 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_exprlist_in_for_stmt1057 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
	public static final BitSet FOLLOW_81_in_for_stmt1059 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_for_stmt1061 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_for_stmt1063 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_for_stmt1065 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
	public static final BitSet FOLLOW_72_in_for_stmt1068 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_for_stmt1070 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_for_stmt1072 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_90_in_try_stmt1090 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_try_stmt1092 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_try_stmt1094 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000A00L});
	public static final BitSet FOLLOW_except_clause_in_try_stmt1109 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_try_stmt1111 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_try_stmt1113 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000300L});
	public static final BitSet FOLLOW_72_in_try_stmt1118 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_try_stmt1120 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_try_stmt1122 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_75_in_try_stmt1138 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_try_stmt1140 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_try_stmt1142 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_73_in_except_clause1161 = new BitSet(new long[]{0x180085C1C4004102L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_except_clause1164 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_except_clause1167 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_except_clause1169 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_simple_stmt_in_suite1182 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_suite1187 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_INDENT_in_suite1189 = new BitSet(new long[]{0x180085C1C4004100L,0x000000001FD9F47EL});
	public static final BitSet FOLLOW_stmt_in_suite1192 = new BitSet(new long[]{0x180085C1C4014100L,0x000000001FD9F47EL});
	public static final BitSet FOLLOW_DEDENT_in_suite1196 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_and_test_in_test1206 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
	public static final BitSet FOLLOW_85_in_test1209 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000100000L});
	public static final BitSet FOLLOW_and_test_in_test1211 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
	public static final BitSet FOLLOW_lambdef_in_test1218 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_not_test_in_and_test1229 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_and_test1232 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000100000L});
	public static final BitSet FOLLOW_not_test_in_and_test1234 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
	public static final BitSet FOLLOW_84_in_not_test1247 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000100000L});
	public static final BitSet FOLLOW_not_test_in_not_test1249 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_comparison_in_not_test1254 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_comparison1263 = new BitSet(new long[]{0x0000103018800012L,0x0000000000160000L});
	public static final BitSet FOLLOW_comp_op_in_comparison1266 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_expr_in_comparison1268 = new BitSet(new long[]{0x0000103018800012L,0x0000000000160000L});
	public static final BitSet FOLLOW_LESS_in_comp_op1279 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GREATER_in_comp_op1283 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EQUAL_in_comp_op1287 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GREATEREQUAL_in_comp_op1291 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LESSEQUAL_in_comp_op1295 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ALT_NOTEQUAL_in_comp_op1299 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOTEQUAL_in_comp_op1303 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_81_in_comp_op1307 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_84_in_comp_op1311 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
	public static final BitSet FOLLOW_81_in_comp_op1313 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_82_in_comp_op1317 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_82_in_comp_op1321 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_84_in_comp_op1323 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_xor_expr_in_expr1332 = new BitSet(new long[]{0x2000000000000002L});
	public static final BitSet FOLLOW_VBAR_in_expr1335 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_xor_expr_in_expr1337 = new BitSet(new long[]{0x2000000000000002L});
	public static final BitSet FOLLOW_and_expr_in_xor_expr1348 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_CIRCUMFLEX_in_xor_expr1351 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_and_expr_in_xor_expr1353 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_shift_expr_in_and_expr1364 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_AMPER_in_and_expr1367 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_shift_expr_in_and_expr1369 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_arith_expr_in_shift_expr1380 = new BitSet(new long[]{0x0008000400000002L});
	public static final BitSet FOLLOW_set_in_shift_expr1383 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_arith_expr_in_shift_expr1389 = new BitSet(new long[]{0x0008000400000002L});
	public static final BitSet FOLLOW_term_in_arith_expr1400 = new BitSet(new long[]{0x0000810000000002L});
	public static final BitSet FOLLOW_set_in_arith_expr1403 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_term_in_arith_expr1409 = new BitSet(new long[]{0x0000810000000002L});
	public static final BitSet FOLLOW_factor_in_term1420 = new BitSet(new long[]{0x0280200000080002L});
	public static final BitSet FOLLOW_set_in_term1423 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_factor_in_term1440 = new BitSet(new long[]{0x0280200000080002L});
	public static final BitSet FOLLOW_set_in_factor1453 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_factor_in_factor1461 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_power_in_factor1466 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_atom_in_power1479 = new BitSet(new long[]{0x0000008080240002L});
	public static final BitSet FOLLOW_trailer_in_power1482 = new BitSet(new long[]{0x0000008080240002L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_power1494 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_factor_in_power1496 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_atom1507 = new BitSet(new long[]{0x182085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_atom1510 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_RPAREN_in_atom1514 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LBRACK_in_atom1519 = new BitSet(new long[]{0x180285C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_listmaker_in_atom1522 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_RBRACK_in_atom1526 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LCURLY_in_atom1531 = new BitSet(new long[]{0x180485C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_dictmaker_in_atom1534 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_RCURLY_in_atom1538 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BACKQUOTE_in_atom1543 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_atom1545 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_BACKQUOTE_in_atom1547 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_atom1552 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atom1557 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LONGINT_in_atom1565 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_atom1573 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COMPLEX_in_atom1581 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_atom1587 = new BitSet(new long[]{0x0800000000000002L});
	public static final BitSet FOLLOW_test_in_listmaker1598 = new BitSet(new long[]{0x0000000000001002L,0x0000000000001000L});
	public static final BitSet FOLLOW_list_for_in_listmaker1602 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_listmaker1614 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_listmaker1616 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_listmaker1623 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_83_in_lambdef1634 = new BitSet(new long[]{0x0200048000200800L});
	public static final BitSet FOLLOW_varargslist_in_lambdef1637 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_lambdef1641 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_lambdef1643 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_trailer1652 = new BitSet(new long[]{0x1A2085C1C4204100L,0x0000000000180000L});
	public static final BitSet FOLLOW_arglist_in_trailer1655 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_RPAREN_in_trailer1659 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LBRACK_in_trailer1664 = new BitSet(new long[]{0x180085C1C4044900L,0x0000000000180000L});
	public static final BitSet FOLLOW_subscriptlist_in_trailer1666 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_RBRACK_in_trailer1668 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOT_in_trailer1673 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_trailer1675 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_subscript_in_subscriptlist1691 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_subscriptlist1701 = new BitSet(new long[]{0x180085C1C4044900L,0x0000000000180000L});
	public static final BitSet FOLLOW_subscript_in_subscriptlist1703 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_subscriptlist1708 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOT_in_subscript1721 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_DOT_in_subscript1723 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_DOT_in_subscript1725 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_test_in_subscript1733 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_COLON_in_subscript1736 = new BitSet(new long[]{0x180085C1C4004902L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_subscript1739 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_sliceop_in_subscript1744 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COLON_in_subscript1756 = new BitSet(new long[]{0x180085C1C4004902L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_subscript1759 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_sliceop_in_subscript1764 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COLON_in_sliceop1778 = new BitSet(new long[]{0x180085C1C4004102L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_sliceop1781 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_exprlist1799 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_exprlist1809 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_expr_in_exprlist1811 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_exprlist1816 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_test_in_testlist1834 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_testlist1845 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_testlist1847 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_testlist1852 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_test_in_dictmaker1873 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_dictmaker1875 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_dictmaker1877 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_dictmaker1895 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_dictmaker1897 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_dictmaker1899 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_dictmaker1901 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_dictmaker1906 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_67_in_classdef1920 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_NAME_in_classdef1922 = new BitSet(new long[]{0x0000008000000800L});
	public static final BitSet FOLLOW_LPAREN_in_classdef1925 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_classdef1927 = new BitSet(new long[]{0x0020000000000000L});
	public static final BitSet FOLLOW_RPAREN_in_classdef1929 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_COLON_in_classdef1933 = new BitSet(new long[]{0x18008DC1C4004100L,0x0000000013D96456L});
	public static final BitSet FOLLOW_suite_in_classdef1935 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_argument_in_arglist1947 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_arglist1950 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_argument_in_arglist1952 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_arglist1966 = new BitSet(new long[]{0x0200000000200002L});
	public static final BitSet FOLLOW_STAR_in_arglist1980 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_arglist1982 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_arglist1985 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_arglist1987 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_arglist1989 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_arglist2005 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_arglist2007 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_arglist2041 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_arglist2043 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_arglist2046 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_arglist2048 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_arglist2050 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLESTAR_in_arglist2062 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_arglist2064 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_test_in_argument2077 = new BitSet(new long[]{0x0000000000000082L});
	public static final BitSet FOLLOW_ASSIGN_in_argument2080 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_argument2082 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_for_in_list_iter2101 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_if_in_list_iter2106 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_76_in_list_for2115 = new BitSet(new long[]{0x180085C1C4004100L});
	public static final BitSet FOLLOW_exprlist_in_list_for2117 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
	public static final BitSet FOLLOW_81_in_list_for2119 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_testlist_in_list_for2121 = new BitSet(new long[]{0x0000000000000002L,0x0000000000009000L});
	public static final BitSet FOLLOW_list_iter_in_list_for2124 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_list_if2135 = new BitSet(new long[]{0x180085C1C4004100L,0x0000000000180000L});
	public static final BitSet FOLLOW_test_in_list_if2137 = new BitSet(new long[]{0x0000000000000002L,0x0000000000009000L});
	public static final BitSet FOLLOW_list_iter_in_list_if2140 = new BitSet(new long[]{0x0000000000000002L});
}
