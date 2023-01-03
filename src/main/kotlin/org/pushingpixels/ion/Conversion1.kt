/*
 * Copyright (c) 2021-2023 Ion Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of the copyright holder nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.pushingpixels.ion

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.swing.Swing
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*

fun main() {
    GlobalScope.launch(Dispatchers.Swing) {
        val frame = JFrame()

        frame.layout = FlowLayout()

        val button = JButton("Start operation!")
        val status = JLabel("Progress")

        frame.add(button)
        frame.add(status)

        button.addActionListener {
            GlobalScope.launch(Dispatchers.Swing) {
                val channel = Channel<Int>()
                GlobalScope.launch {
                    for (x in 1..5) {
                        println("Sending $x " + SwingUtilities.isEventDispatchThread())
                        // This is happening off the main thread
                        channel.send(x)
                        // Emulating long-running background processing
                        delay(1000L)
                    }
                    // Close the channel as we're done processing
                    channel.close()
                }
                // The next loop keeps on going as long as the channel is not closed
                for (y in channel) {
                    println("Processing $y " + SwingUtilities.isEventDispatchThread())

                    status.text = "Progress $y"
                }
                status.text = "Done!"
            }
        }

        frame.size = Dimension(600, 400)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        frame.isVisible = true
    }
}
