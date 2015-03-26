import gameCanvasUtil.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

/**
 * アプリケーションのメイン部分(全てはここから始まる)
 *
 * スレッド、Event処理とかを一手に引き受けるクラス といいつつ、やることは基本他にたらいまわし。
 *
 * あと、ウインドウそのもののクラス
 */

public class GameApp extends JFrame implements WindowListener, Runnable
{
    // / serialVersionUID
    private static final long serialVersionUID = 1L;

    // / object that manage time
    private FPSManager timer = null;

    // / object that manage drawing
    private AppCanvas canvas = null;

    // / thread
    private Thread th = null;

    // break when thread become false
    private boolean thFlag = true;

    /**
     * ゲームモード
     *
     * @param args 引数
     */
    public static void main(String[] args)
    {
        new GameApp();
    }

    /**
     * コンストラクタ ウインドウの大きさ設定などを行ってます。
     */
    private GameApp()
    {
        super("FrameTest");
        timer = FPSManager.getInstance();
        timer.init(GameCanvas.CONFIG_FPS);
        GameCanvas.getInstance().init(this, new Game());

        //fix the size
        setResizable(false);
        // when X was pushed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // リスナーに自身を登録
        addWindowListener(this);

        // コンポーネント追加
        canvas = new AppCanvas();
        add(this.canvas, BorderLayout.CENTER);

        /* pack -> setPreferredSize の順に実行するとサイズ設定がうまくいく */
        // コンポーネントのサイズに合わせてサイズを変更する
        pack();

        // コンポーネント追加後に、内側のサイズ（ウインドウ枠をのぞいたサイズ）からウインドウサイズを指定
        setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));

        // draw window
        setVisible(true);

        th = new Thread(this);
        th.start();
    }

    /**
     * called when window opened
     *
     * @param evt :window event
     */
    public void windowOpened(WindowEvent evt)
    {
    }

    /**
     * called when x was pushed
     *
     * @param evt :window event
     */
    public void windowClosing(WindowEvent evt)
    {
        GameCanvas.getInstance().finalize();
        thFlag = false;
    }

    /**
     *called when window was closed
     *
     * @param evt :window event
     */
    public void windowClosed(WindowEvent evt)
    {
    }

    /**
     * called when window iconfied
     *
     * @param evt :window event
     */
    public void windowIconified(WindowEvent evt)
    {
    }

    /**
     * called when window deiconified
     *
     * @param evt :window event
     */
    public void windowDeiconified(WindowEvent evt)
    {
    }

    /**
     * called when window become active
     *
     * @param evt :window event
     */
    public void windowActivated(WindowEvent evt)
    {
    }

    /**
     *called when window is inactive
     *
     * @param evt :window event
     */
    public void windowDeactivated(WindowEvent evt)
    {
    }

    /**
     * main thred
     */
    public void run()
    {
        while (thFlag)
        {
            this.canvas.updateMessage();
            this.canvas.drawMessage();
            timer.Wait();
        }
    }

}
