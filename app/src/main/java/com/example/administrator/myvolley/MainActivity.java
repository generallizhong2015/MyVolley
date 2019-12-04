package com.example.administrator.myvolley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * by generallizhong
 */

public class MainActivity extends Activity {

	private Button get;
	private Button post;
	private Button json;
	private Button imagerequest;
	private Button imageload;
	private Button netWorkImageView;

	private ImageView iv;
	private NetworkImageView network;
	private TextView tv_volley_result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initview();
		initListener();

	}
	public void initview()//把需要初始化的控件的逻辑都写在这里是一个很好的编程范式
	{

		get= (Button) findViewById(R.id.get);
		post= (Button) findViewById(R.id.post);
		json= (Button) findViewById(R.id.json);
		imagerequest= (Button) findViewById(R.id.ImageRquest);
		imageload= (Button) findViewById(R.id.ImageLoader);
		netWorkImageView= (Button) findViewById(R.id.NetWorkImageView);
		iv= (ImageView) findViewById(R.id.iv_volley);
		network= (NetworkImageView) findViewById(R.id.NetWork);
		tv_volley_result= (TextView) findViewById(R.id.tv_volley_result);



	}
	public void initListener()
	{
		get.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//创建一个请求队列
				RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
				//创建一个请求
				String url="http://gank.io/api/xiandu/category/wow";
				StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
					//正确接受数据之后的回调
					@Override
					public void onResponse(String response) {
						tv_volley_result.setText(response);
					}
				}, new Response.ErrorListener() {//发生异常之后的监听回调
					@Override
					public void onErrorResponse(VolleyError error) {
						tv_volley_result.setText("加载错误"+error);
					}
				});
				//将创建的请求添加到请求队列当中
				requestQueue.add(stringRequest);
			}
		});


		post.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 1 创建一个请求队列
				RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

				// 2 创建一个post请求
				String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
				StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						tv_volley_result.setText(s);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						tv_volley_result.setText("请求失败" + volleyError);
					}
				}) {
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {

						Map<String, String> map = new HashMap<String, String>();
//                        map.put("value1","param1");

						return map;
					}
				};

				// 3 将post请求添加到队列中
				requestQueue.add(stringRequest);




			}
		});

		json.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
// 1 创建一个请求队列
				RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

				// 2 创建一个请求
				String url = "http://gank.io/api/xiandu/category/wow";
				//JsonArrayRequest jsonObjectRequest2=......
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						tv_volley_result.setText(jsonObject.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						tv_volley_result.setText("请求失败" + volleyError);
					}
				});

				// 3 将创建的请求添加到请求队列中
				requestQueue.add(jsonObjectRequest);

//这一步完成之后就可以使用我们的json解析了

			}
		});

		imagerequest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 1 创建一个请求队列
				RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

				// 2 创建一个图片的请求
				String url = "http://img5.mtime.cn/mg/2016/10/11/160347.30270341.jpg";
				ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						// 正确接收到图片
						iv.setVisibility(View.VISIBLE);//将图片设置为可见
						iv.setImageBitmap(bitmap);//将接受到的图片Bitmap对象传入到我们的imageview当中
					}
				}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
					//前面两个0，0的参数表示的是我们加载图片最大宽度和高度，后面的Bitmap.Config.RGB_565表示图片的质量
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						iv.setImageResource(R.drawable.flickr);
					}
				});

				// 3 将请求添加到请求队列中
				requestQueue.add(imageRequest);

			}
		});


		imageload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);


				ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
					@Override
					public Bitmap getBitmap(String s) {
						return null;
					}

					@Override
					public void putBitmap(String s, Bitmap bitmap) {

					}
				});


				// 加载图片
				String url = "http://img5.mtime.cn/mg/2016/10/11/160347.30270341.jpg";
				iv.setVisibility(View.VISIBLE);
				ImageLoader.ImageListener imageListener = imageLoader.getImageListener(iv, R.drawable.flickr, R.drawable.flickr);
				//上述代码后面两个参数分别表示的是默认的图片和加载失败之后的图片
				imageLoader.get(url, imageListener);
			}
		});

		netWorkImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				// 让控件显示
				network.setVisibility(View.VISIBLE);

				// 创建一个请求队列
				RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

				// 创建一个Imageloader
				ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());

				// 默认图片和异常图片设置
				network.setDefaultImageResId(R.drawable.flickr);
				network.setErrorImageResId(R.drawable.flickr);

				// 加载图片
				String url = "http://img5.mtime.cn/mg/2016/10/11/160347.30270341.jpg";
				network.setImageUrl(url, imageLoader);


			}
		});

	}

}
