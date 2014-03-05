/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stac.ui
import groovy.swing.SwingBuilder
import groovy.swing.SwingXBuilder

import javax.swing.*
import java.awt.*
import java.awt.geom.*
import java.awt.BorderLayout as BL;
import java.awt.GridBagConstraints as GBC;

import java.awt.GridBagConstraints
import javax.swing.JTabbedPane
import javax.swing.SwingConstants
import java.util.GregorianCalendar

import org.jdesktop.swingx.calendar.DaySelectionModel
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode
import org.jdesktop.swingx.painter.*
import org.jdesktop.swingx.painter.effects.*



swing = new SwingXBuilder()

swing.frame(title: 'SwingX', defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
    size: [800,600], 
    minimumSize: [350,500],
    show: true, locationRelativeTo: null) {



    gridBagLayout()

    tabbedPane(tabPlacement:JTabbedPane.LEFT, weightx:1.0, weighty:1.0, fill:GridBagConstraints.BOTH) {
        vbox(title:'TitledPanel') {
            titledPanel(title:"Panel 1") {
                label("Content goes here")
            }
            titledPanel(title:"Panel 2") {
                label("Content goes here as well")
            }
            titledPanel(title:"Panel 3") {
                label("Content goes here in addition to the previous two")
            }
        }

        vbox(title:'BusyLabel') {
            // default painter is hosed right now :(  Not a groovy problem
	    busyLabel(id:'busy', enabled:true, busy:true, size:[100, 100], preferredSize:[100, 100], minimumSize:[100, 100], maximumSize:[100, 100])
            checkBox("I'm Busy", selected:bind(target:busy, targetProperty:'busy'))	
        }

        vbox(title:'Label') {
            hbox {
                label('Griffon', 
                    horizontalTextPosition: SwingConstants.CENTER, verticalTextPosition: SwingConstants.BOTTOM)
                label('Griffon', 
                    horizontalTextPosition: SwingConstants.CENTER, verticalTextPosition: SwingConstants.BOTTOM,
                    textRotation:Math.PI/2)
            }
            hbox {
                label('Griffon', 
                    horizontalTextPosition: SwingConstants.CENTER, verticalTextPosition: SwingConstants.BOTTOM,
                    textRotation:Math.PI)
                label('Griffon', 
                    horizontalTextPosition: SwingConstants.CENTER, verticalTextPosition: SwingConstants.BOTTOM,
                    textRotation:Math.PI/2*3)
            }
        }

        vbox(title:'DatePicker'){
            hbox {
                label('Default DatePicker: ')
                datePicker()
            }
            hbox {
                label('Preselected Date: ')
                datePicker(date:new GregorianCalendar(2008,10,10).getTime())
            }
        }
        vbox(title:'GradientChooser') {
            gradientChooser()
        }

        vbox(title:'MonthView') {
            hbox {
                label('Default MonthView: ')
                monthView()
            }
            hbox {
                label('MonthView Interval: ')
                def model = new DaySelectionModel(selectionMode:SelectionMode.SINGLE_INTERVAL_SELECTION)
                model.addSelectionInterval(new GregorianCalendar(2008,10,10).getTime(), new GregorianCalendar(2008,10,16).getTime())
                monthView(firstDisplayedDay:new GregorianCalendar(2008,10,10).getTime(), model:model)
            }
        }
        vbox(title:'Graph') {
            hbox {
                label('Plain Graph: ')
                graph()
            }
            hbox {
                label('Sine curve:  ')
                graph(plots:[[Color.GREEN,{value -> Math.sin(value)}]])
            }
        }
        vbox(title:'Painters') {
            hbox {
                label('Pinstripe:    ')
                def painter = pinstripePainter(angle:45, paint:Color.BLUE, spacing:10.4d, stripeWidth:3.6d)
                panel(backgroundPainter:painter)
            }
            hbox {
                label('Matte:        ')
                def painter = mattePainter(fillPaint:Color.RED)
                panel(backgroundPainter:painter)
            }
            hbox {
                label('Checkerboard: ')
                def painter = checkerboardPainter(squareSize:40.0d, lightPaint:Color.WHITE, darkPaint:Color.BLACK)
                panel(backgroundPainter:painter)
            }
            hbox {
                label('Capsule:      ')
                def painter = capsulePainter(portion:CapsulePainter.Portion.Bottom)
                panel(backgroundPainter:painter)
            }
            hbox {
                label('Shape:        ')
                def painter = shapePainter(shape:new Rectangle2D.Double(0, 0, 50, 50),horizontalAlignment:AbstractLayoutPainter.HorizontalAlignment.RIGHT)
                panel(backgroundPainter:painter)
            }
            hbox {
                label('Text:         ')
                def painter = textPainter(font:new Font("Tahoma", Font.BOLD, 32), text:"Test Text")
                panel(backgroundPainter:painter)
            }
            hbox {
                label('Compound:     ')
                def ap = compoundPainter() {
                    mattePainter(fillPaint:Color.GRAY)
                    glossPainter(position: GlossPainter.GlossPosition.TOP)
                }
                panel(backgroundPainter:ap)
            }
            hbox {
                label('Alpha:        ')
                def ap = alphaPainter(alpha:0.25f) {
                    mattePainter(fillPaint:Color.GRAY)
                    glossPainter(position: GlossPainter.GlossPosition.TOP)
                }
                panel(backgroundPainter:ap)
            }
        }
        vbox(title:'MultiSplitPane') {
            multiSplitPane() {
		split() {
                    leaf(name:"left")
                    divider()
                    split() {
                        leaf(name:"top")
                        divider()
                        leaf(name:"bottom")
                    }
		}
		button(text:"Left Button", constraints:"left")
		button(text:"Right Button", constraints:"right")
		button(text:"Top Button", constraints:"top")
		button(text:"Bottom Button", constraints:"bottom")
            }

        }
    
    }


}