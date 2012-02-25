package com.paulos.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DialogActivity extends Activity {
	CharSequence[] items = { "Google", "Apple", "Microsoft"};
	boolean[] itemsChecked = new boolean [items.length];
	
	private ProgressDialog _progressDialog;
	private int _progress = 0;
	private Handler _progressHandler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button choice_btn = (Button) findViewById(R.id.button1);
		choice_btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showDialog(0);
			}
		});
		

		Button progress_btn = (Button) findViewById(R.id.button2);
		progress_btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showDialog(1);
				_progress = 0;
				_progressDialog.setProgress(0);
				_progressHandler.sendEmptyMessage(0);
			}
		});
		
		
		_progressHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (_progress >= 100) {
					_progressDialog.dismiss();
				} else {
					_progress++;
					_progressDialog.incrementProgressBy(1);
					_progressHandler.sendEmptyMessageDelayed(0, 100);
				}
			}
		};
    }
    
    
	public Dialog onCreateDialog(int id) {
		switch (id) {

		case 1:
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setTitle("Dawnloading File");
			_progressDialog.setIcon(R.drawable.ic_launcher);
			_progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			_progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hide", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getBaseContext(), "Hide Clicked", Toast.LENGTH_SHORT).show();
				}
			});
			_progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getBaseContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
				}
			});	
	return _progressDialog;
		
		case 0:
			return new AlertDialog.Builder(this)
					.setTitle("This is a dialog with simple text")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(getBaseContext(),
											"OK clicked!", Toast.LENGTH_SHORT)
											.show();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(getBaseContext(),
											"Cancel clicked!",
											Toast.LENGTH_SHORT).show();

								}
							})
					.setMultiChoiceItems(items, itemsChecked,
							new DialogInterface.OnMultiChoiceClickListener() {

								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									Toast.makeText(
											getBaseContext(),
											items[which]
													+ (isChecked ? " checked"
															: " unchecked"),
											Toast.LENGTH_SHORT).show();
								}
							}).create();
		}
		return null;
		
	}
}