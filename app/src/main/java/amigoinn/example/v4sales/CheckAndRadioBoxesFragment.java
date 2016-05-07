package amigoinn.example.v4sales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.v4sales.R;

public class CheckAndRadioBoxesFragment extends Fragment {

	public static WebView wvLoader;
	Context context;
	String url1;
	ProgressDialog dialog;
	public static CheckAndRadioBoxesFragment newInstance() {
		return new CheckAndRadioBoxesFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_check_and_radio_boxes, container, false);
		context=rootView.getContext();
//		SharedPreferences preference=context.getSharedPreferences("url",Context.MODE_PRIVATE);
//		url1=preference.getString("url", "http://www.google.com");
//		wvLoader=(WebView)rootView.findViewById(R.id.webView1);
//		wvLoader.getSettings().setJavaScriptEnabled(true);
//		wvLoader.getSettings().setLoadWithOverviewMode(true);
//		wvLoader.getSettings().setUseWideViewPort(true);
//		//wvLoader.getSettings().setBuiltInZoomControls(true);
//		//wvLoader.getSettings().setPluginState(WebSettings.PluginState.ON);
//		//wvLoader.getSettings().setPluginsEnabled(true);
//		//wvLoader.setWebViewClient(new HelloWebViewClient());
//		dialog=new ProgressDialog(context);
//		dialog.setMessage("Please Wait for a moment!");
//		dialog.setIndeterminate(true);
//		dialog.setCanceledOnTouchOutside(false);
//
//		Drawable customDrawable= context.getResources().getDrawable(R.drawable.customdialog);
//		dialog.setIndeterminateDrawable(customDrawable);
//		wvLoader.setWebViewClient(new MyWebViewClient());
//
//		if(url1 == null){
//			//myUrl = myBlogAddr;
//		}
//		wvLoader.loadUrl(url1);
//
//
//
//		if(dialog.isShowing()) {
//			dialog.cancel();
//		}
		TextView txtMail=(TextView)rootView.findViewById(R.id.txtContact);
		txtMail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setType("plain/text");
//              sendIntent.setData(Uri.parse("test@gmail.com"));
				sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
				sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@v4account.com" });
				startActivity(sendIntent);
			}
		});
		return rootView;
	}
	public static void myOnKeyDown(int key_code)
	{
		//do whatever you want here
		if ((key_code == KeyEvent.KEYCODE_BACK) && CheckAndRadioBoxesFragment.wvLoader.canGoBack())
		{

			CheckAndRadioBoxesFragment.wvLoader.goBack();

		}
	}
	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			url1 = url;
			view.loadUrl(url1);
			return true;
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			dialog.cancel();
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {


			dialog.show();
			super.onPageStarted(view, url, favicon);
		}
	}
}
