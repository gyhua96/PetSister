package cn.edu.hfut.xc.gyh.petsister;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ImageDetailActivity extends Activity {

	private ArrayList<String> mDatas;
	private int mPosition;
	private int mLocationX;
	private int mLocationY;
	private String path;
	private int mHeight;
	SmoothImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		path=(String) getIntent().getSerializableExtra("url");
		System.out.println(path);

		imageView = new SmoothImageView(this);
		//Bitmap btm= decodeSampledBitmapFromPath(path,1080,1920);
		//System.out.println(btm.toString());
		loadPhoto loadphoto=new loadPhoto();
		loadphoto.execute(null,null);

		//ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);
//		imageView.setImageResource(R.drawable.temp);
		// ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
		// 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		// 0.5f);
		// scaleAnimation.setDuration(300);
		// scaleAnimation.setInterpolator(new AccelerateInterpolator());
		// imageView.startAnimation(scaleAnimation);

	}

	@Override
	public void onBackPressed() {
		imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
			@Override
			public void onTransformComplete(int mode) {
				if (mode == 2) {
					finish();
				}
			}
		});
		imageView.transformOut();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}
	protected Bitmap decodeSampledBitmapFromPath(String path, int width,
												 int height)
	{
		// 获得图片的宽和高，并不把图片加载到内存中
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options,
				width, height);

		// 使用获得到的InSampleSize再次解析图片
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		//System.out.println("load"+path+"load");
		return bitmap;
	}

	class loadPhoto extends AsyncTask<Void, Void, Boolean>{
		Bitmap btm;
		@Override
		protected Boolean doInBackground(Void... params) {
			btm=downloadImgByUrl(path,imageView);
			System.out.println(path+"path"+btm);
			//imageView.setOriginalInfo(1920, 1080, 100, 100);


		return true;
		}
		@Override
		protected void onPostExecute(final Boolean success) {
			//ImageView iv=(ImageView) findViewById(R.id.takephoto);
			imageView.setImageBitmap(btm);
			imageView.transformIn();
			imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
			imageView.setScaleType(ScaleType.FIT_CENTER);
			setContentView(imageView);
		}

	}
	public Bitmap downloadImgByUrl(String urlStr, ImageView imageview)
	{
		//FileOutputStream fos = null;
		InputStream is = null;
		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			System.out.println("conn"+conn);
			is = new BufferedInputStream(conn.getInputStream());
			//is.mark(is.available());
			System.out.println(urlStr);
			System.out.println("is"+is);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			Bitmap bitmap;// = BitmapFactory.decodeStream(is, null, opts);

			//获取imageview想要显示的宽和高
			//ImageSizeUtil.ImageSize imageViewSize = ImageSizeUtil.getImageViewSize(imageview);
			opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts,
					imageview.getWidth(), imageview.getHeight());

			opts.inJustDecodeBounds = false;

			bitmap = BitmapFactory.decodeStream(is, null, opts);
			System.out.println("btmap"+bitmap);
			is.close();
			conn.disconnect();
			return bitmap;

		} catch (Exception e)
		{
			e.printStackTrace();
		}



		return null;

	}
}
