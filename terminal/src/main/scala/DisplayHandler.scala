import customjavafx.scene.control._
import customjavafx.scene.layout._
import scalafx.scene.media.AudioClip
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.ObservableList
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.layout.{ Region, VBox}
import javafx.scene.transform.Rotate
import javafx.util.Duration
import javafx.animation._
import fs2.io.fx.{Display, Host, Menu}
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout", "customjavafx.scene.control"))
class DisplayHandler(
  val gameBox: VBox,
  val tableNumber: Label,
  val handBetMin: Label,
  val handBetMax: Label,
  val tieBetMin: Label,
  val tieBetMax: Label,
  val pairBetMin: Label,
  val pairBetMax: Label,
  val logo: Region)(implicit display: Display, writer: Host[Menu, Unit], startMenu: Menu) {


  tableNumber.setText(startMenu.name)
  handBetMin.setText(startMenu.handBetMin)
  handBetMax.setText(startMenu.handBetMax)
  tieBetMin.setText(startMenu.tieBetMin)
  tieBetMax.setText(startMenu.tieBetMax)
  pairBetMin.setText(startMenu.pairBetMin)
  pairBetMax.setText(startMenu.pairBetMax)



  val logoAnimation: RotateTransition = new RotateTransition(Duration.millis(5000), logo)
  logoAnimation.setAxis(Rotate.Y_AXIS)
  logoAnimation.setByAngle(180)
  logoAnimation.setCycleCount(2)
  logoAnimation.setInterpolator(Interpolator.LINEAR)
  logoAnimation.setAutoReverse(true)
  logoAnimation.setDelay(Duration.millis(3000))
  logoAnimation.play()

  logoAnimation.setOnFinished(new EventHandler[ActionEvent] {
    override def handle(t: ActionEvent): Unit = {
      logoAnimation.play()
    }
  })

  display.root.addEventHandler(
    KeyEvent.KEY_PRESSED,
    new EventHandler[KeyEvent] {

      def saveMenuToDisk(): Unit = {
        val task = writer.request(
          Menu(
            tableNumber.getText,
            handBetMin.getText,
            handBetMax.getText,
            tieBetMin.getText,
            tieBetMax.getText,
            pairBetMin.getText,
            pairBetMax.getText))
        task.run()
      }

      //      if (java.awt.Toolkit.getDefaultToolkit.getLockingKeyState(java.awt.event.KeyEvent.VK_NUM_LOCK)) {
      //      }

      override def handle(t: KeyEvent): Unit = {
        t.getCode match {
          case KeyCode.ENTER =>
          case _ =>
        }

      }
    }
  )

  //  beadRoad.getCountProperty
  //    .addListener(new ChangeListener[Number] {
  //      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
  //         new AudioClip(getClass.getResource("/sounds/banker.mp3").toExternalForm).play()
  //      }
  //    })

  //  bigRoad.bigEyeRoadListProperty
  //    .addListener(new ChangeListener[ObservableList[BigEyeRoadLabel]] {
  //      override def changed(
  //                            observableValue: ObservableValue[_ <: ObservableList[BigEyeRoadLabel]],
  //                            t: ObservableList[BigEyeRoadLabel],
  //                            t1: ObservableList[BigEyeRoadLabel]): Unit = {
  //        if (!t1.isEmpty) bigEyeRoad.ReArrangeElements(t1)
  //        else {
  //          bigEyeRoad.Reset()
  //        }
  //      }
  //    })



  display.root.setOnCloseRequest(_ => {
    display.exit()
  })

}
