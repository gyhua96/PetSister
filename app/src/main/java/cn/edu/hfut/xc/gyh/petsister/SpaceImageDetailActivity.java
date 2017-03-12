package cn.edu.hfut.xc.gyh.petsister;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;

public class SpaceImageDetailActivity extends Activity {

	private ArrayList<String> mDatas;
	private int mPosition;
	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	SmoothImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String path=(String) getIntent().getSerializableExtra("image");
		System.out.println(path);

		imageView = new SmoothImageView(this);
		Bitmap btm= decodeSampledBitmapFromPath(path,1080,1920);
		System.out.println(btm.toString());
		imageView.setImageBitmap(btm);
		//imageView.setOriginalInfo(1920, 1080, 100, 100);
		imageView.transformIn();
		imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		setContentView(imageView);

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
	private Bitmap loadImageFromLocal(String path,
									  ImageView imageView)
	{
		Bitmap bm;
		// 加载图片
		// 图片的压缩
		// 1、获得图片需要显示的大小
		//ImageSizeUtil.ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
		// 2、压缩图片
		bm = decodeSampledBitmapFromPath(path, imageView.getWidth(),
				imageView.getHeight());
		return bm;
	}
}
