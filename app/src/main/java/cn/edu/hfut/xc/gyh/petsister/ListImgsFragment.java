package cn.edu.hfut.xc.gyh.petsister;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.hfut.xc.gyh.petsister.ImageLoader.Type;
/**
 * http://blog.csdn.net/lmj623565791/article/details/41874561
 * @author zhy
 *
 */
public class ListImgsFragment extends Fragment
{
	private GridView mGridView;
	private String[] mUrlStrs;
	private ImageLoader mImageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mImageLoader = ImageLoader.getInstance(3, Type.LIFO);
		GetpicsTask getpicsTask=new GetpicsTask();
		getpicsTask.execute(null,null);
		//System.out.println(mUrlStrs.toString());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_list_imgs, container,
				false);
		mGridView = (GridView) view.findViewById(R.id.id_gridview);
		//setUpAdapter();
		return view;
	}

	private void setUpAdapter()
	{
		if (getActivity() == null || mGridView == null)
			return;

		if (mUrlStrs != null)
		{
			mGridView.setAdapter(new ListImgItemAdaper(getActivity(), 0,
					mUrlStrs));
		} else
		{
			mGridView.setAdapter(null);
		}

	}

	private class ListImgItemAdaper extends ArrayAdapter<String>
	{

		public ListImgItemAdaper(Context context, int resource, String[] datas)
		{
			super(getActivity(), 0, datas);
			Log.e("TAG", "ListImgItemAdaper");
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.item_fragment_list_imgs, parent, false);
			}
			final ImageView imageview = (ImageView) convertView
					.findViewById(R.id.id_img);
			imageview.setImageResource(R.drawable.pictures_no);
			mImageLoader.loadImage(getItem(position), imageview, true);
			//System.out.println(getItem(position)+"aaaa");

			convertView.setOnClickListener(new View.OnClickListener() {//视图设置一个点击事件的监听器
				@Override
				public void onClick(View v) {//重写点击事件的回调方法

					//System.out.println(this.toString());
					//System.out.println(getItem(position));

					Intent intent = new Intent(Gallery.mActivity,SpaceImageDetailActivity.class);
					intent.putExtra("image", mImageLoader.getDiskCacheDir(imageview.getContext(),mImageLoader.md5(getItem(position))).toString());
					Gallery.mActivity.startActivity(intent);
					System.out.println(mImageLoader.getDiskCacheDir(imageview.getContext(),mImageLoader.md5(getItem(position))).toString());
				}
			});
			return convertView;
		}

	}
	public class GetpicsTask extends AsyncTask<Void, Void, Boolean> {
		private String result;
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			try {
				// Simulate network access.
				System.out.print("urlPath"+Config.PicHost);
				String urlPath = Config.PicHost; //new String("http://www.gongyuhua.cn/pic.php");
				System.out.println(urlPath+"urlPath");
				String param="a=b";//"email="+ URLEncoder.encode(mEmail,"UTF-8")+"&pwd="+URLEncoder.encode(mPassword);
				//建立连接
				URL url=new URL(urlPath);
				HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
				//设置参数
				httpConn.setDoOutput(true);   //需要输出
				httpConn.setDoInput(true);   //需要输入
				httpConn.setUseCaches(false);  //不允许缓存
				httpConn.setRequestMethod("POST");   //设置POST方式连接
				//设置请求属性
				httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
				httpConn.setRequestProperty("Charset", "UTF-8");
				//连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
				httpConn.connect();
				//建立输入流，向指向的URL传入参数
				DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
				dos.writeBytes(param);
				dos.flush();
				dos.close();
				//获得响应状态
				int resultCode=httpConn.getResponseCode();
				if(HttpURLConnection.HTTP_OK==resultCode) {
					StringBuffer sb = new StringBuffer();
					String readLine = new String();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
					while ((readLine = responseReader.readLine()) != null) {
						sb.append(readLine).append("\n");
					}

					responseReader.close();
					result =sb.toString();
					System.out.println(result);

				}
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}


			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mUrlStrs=result.split(",");
			System.out.println(mUrlStrs[0]);

			System.out.println(mUrlStrs[1]);
			setUpAdapter();
		}
	}
}
