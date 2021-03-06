import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

//JFrameを継承
public class JavaPaint extends JFrame implements ActionListener{


	DrawGraphics graphics;
	JCheckBoxMenuItem menuitem2_3 ,menuitem2_4;

	public JavaPaint(){
		JFrame frame  = new JFrame("Draw Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// メニューバーの作成.
		JMenuBar menubar = new JMenuBar();

		JMenu menu1 = new JMenu("ファイル(F)");
		menu1.setMnemonic(KeyEvent.VK_F);
		JMenu menu2 = new JMenu("編集(E)");
		menu2.setMnemonic(KeyEvent.VK_E);
		menubar.add(menu1);
		menubar.add(menu2);

		JMenu submenu1 = new JMenu("画像の形式を変更");
		JRadioButtonMenuItem radiomenuitem1 = new JRadioButtonMenuItem("PNG");
		JRadioButtonMenuItem radiomenuitem2 = new JRadioButtonMenuItem("GIF");
		JRadioButtonMenuItem radiomenuitem3 = new JRadioButtonMenuItem("JPG");
		submenu1.add(radiomenuitem1);
		submenu1.add(radiomenuitem2);
		submenu1.add(radiomenuitem3);
		radiomenuitem1.setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(radiomenuitem1);
		group.add(radiomenuitem2);
		group.add(radiomenuitem3);

		JMenuItem menuitem1_1 = new JMenuItem("新規(N)");
		menuitem1_1.setMnemonic(KeyEvent.VK_N);
		menuitem1_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuitem1_2 = new JMenuItem("開く(O)");
		menuitem1_2.setMnemonic(KeyEvent.VK_O);
		menuitem1_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuitem1_3 = new JMenuItem("名前を付けて保存(A)");
		menuitem1_3.setMnemonic(KeyEvent.VK_A);
		menuitem1_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuitem1_4 = new JMenuItem("上書き保存(S)");
		menuitem1_4.setMnemonic(KeyEvent.VK_S);
		menuitem1_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuitem1_5 = new JMenuItem("画像で出力(E)");
		menuitem1_5.setMnemonic(KeyEvent.VK_E);
		menuitem1_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuitem1_6 = new JMenuItem("終了(Q)");
		menuitem1_6.setMnemonic(KeyEvent.VK_Q);
		menuitem1_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		menu1.add(menuitem1_1);
		menu1.add(menuitem1_2);
		menu1.addSeparator();
		menu1.add(menuitem1_3);
		menu1.add(menuitem1_4);
		menu1.add(submenu1);
		menu1.add(menuitem1_5);
		menu1.addSeparator();
		menu1.add(menuitem1_6);

		JMenuItem menuitem2_1 = new JMenuItem("元に戻す(U)");
		menuitem2_1.setMnemonic(KeyEvent.VK_U);
		menuitem2_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuitem2_2 = new JMenuItem("やり直し(R)");
		menuitem2_2.setMnemonic(KeyEvent.VK_R);
		menuitem2_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		menuitem2_3 = new JCheckBoxMenuItem("アンチエイリアス");
		menuitem2_3.setSelected(true);
		menuitem2_4 = new JCheckBoxMenuItem("グリッド線を表示");
		menuitem2_4.setSelected(true);
		menu2.add(menuitem2_1);
		menu2.add(menuitem2_2);
		menu2.addSeparator();
		menu2.add(menuitem2_3);
		menu2.add(menuitem2_4);

		//ActionListenerの設定
		menuitem1_1.addActionListener(this);
		menuitem1_2.addActionListener(this);
		menuitem1_3.addActionListener(this);
		menuitem1_4.addActionListener(this);
		menuitem1_5.addActionListener(this);
		menuitem1_6.addActionListener(this);
		menuitem2_1.addActionListener(this);
		menuitem2_2.addActionListener(this);
		menuitem2_3.addActionListener(this);
		menuitem2_4.addActionListener(this);
		radiomenuitem1.addActionListener(this);
		radiomenuitem2.addActionListener(this);
		radiomenuitem3.addActionListener(this);

		// ActionCommandの設定
		menuitem1_1.setActionCommand("newFile");
		menuitem1_2.setActionCommand("dataOpen");
		menuitem1_3.setActionCommand("saveAs");
		menuitem1_4.setActionCommand("save");
		menuitem1_5.setActionCommand("dataExport");
		menuitem1_6.setActionCommand("exit");
		menuitem2_1.setActionCommand("unDo");
		menuitem2_2.setActionCommand("reDo");
		menuitem2_3.setActionCommand("antiAliasing");
		menuitem2_4.setActionCommand("setGrid");
		radiomenuitem1.setActionCommand("PNG");
		radiomenuitem2.setActionCommand("GIF");
		radiomenuitem3.setActionCommand("JPG");

		frame.setJMenuBar(menubar);

		graphics = new DrawGraphics();
		frame.add(graphics, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Object obj=e.getSource();

		if(actionCommand.equals("newFile")){						// 状態をリセット刷る．
			graphics.newFile();										// DrawGraphicsクラスのnewFile()メソッドで状態をリセット
		}else if(actionCommand.equals("dataOpen")){
			graphics.dataOpen();									// dataOpenメソッドでベクター情報ファイルを開く
		}else if(actionCommand.equals("saveAs")){					// ベクター情報を保存する
			JFileChooser filechooser = new JFileChooser();
			filechooser.setDialogTitle("名前を付けて保存");			// titleを変更
			int selected = filechooser.showSaveDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				System.out.println(file.getName());
				graphics.saveAs(file);
			}else if (selected == JFileChooser.CANCEL_OPTION){
				System.out.println("キャンセルされました");			// キャンセルボタンが押されたら，ターミナルに表示
			}else if (selected == JFileChooser.ERROR_OPTION){
				System.out.println("エラー又は取消しがありました");
			}
		}else if(actionCommand.equals("save")){						// ベクター情報を上書き保存する．
			graphics.save();
		}else if(actionCommand.equals("PNG")){						// 保存する画像の形式をPNGに設定
			graphics.imageOp(1);
		}else if(actionCommand.equals("GIF")){						// 保存する画像の形式をGIFに設定
			graphics.imageOp(2);
		}else if(actionCommand.equals("JPG")){						// 保存する画像の形式をJPGに設定
			graphics.imageOp(3);
		}else if(actionCommand.equals("dataExport")){				// 画像を出力
			JFileChooser filechooser = new JFileChooser();
			filechooser.setDialogTitle("画像を出力"); // titleを変更
			int selected = filechooser.showSaveDialog(this);

			if (selected == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				graphics.dataExport(file);
			}else if (selected == JFileChooser.CANCEL_OPTION){
				System.out.println("キャンセルされました");
			}else if (selected == JFileChooser.ERROR_OPTION){
				System.out.println("エラー又は取消しがありました");
			}
		}else if(actionCommand.equals("unDo")){						// UNDOの実行
			graphics.unDo();
		}else if(actionCommand.equals("reDo")){						// REDOの実行
			graphics.reDo();
		}else if(actionCommand.equals("antiAliasing")){				// アンチエイリアシングの切り替え
			graphics.setAntiAliasing(menuitem2_3.isSelected());
		}else if(actionCommand.equals("setGrid")){					// Grid線の切り替え
			graphics.setGrid(menuitem2_4.isSelected());
		}else if(actionCommand.equals("exit")){						// プログラムの終了
			System.exit(0);
		}
	}

	public static void main(String args[]){
		new JavaPaint();											// JavaPaintクラスの実行
	}
}
