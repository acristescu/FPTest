package io.zenandroid.fptest.accountdetails;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.zenandroid.fptest.R;
import io.zenandroid.fptest.base.BaseActivity;
import io.zenandroid.fptest.dagger.Injector;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class AccountProfileActivity extends BaseActivity implements AccountProfileContract.View {

	@BindView(R.id.input_email) EditText emailText;
	@BindView(R.id.input_password) EditText passwordText;
	@BindView(R.id.avatar) ImageView avatarView;

	private AccountProfileContract.Presenter presenter;
	private static final int PICK_IMAGE_CAMERA = 1;
	private static final int PICK_IMAGE_GALLERY = 2;

	private File photoFile;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_profile);
		Injector.get().inject(this);
		ButterKnife.bind(this);

		getSupportActionBar().hide();
		presenter = new AccountProfilePresenter(this);
		presenter.start();
	}

	@OnClick(R.id.avatar)
	public void onImageClicked() {
		final String[] options = {"Take Photo", "Choose From Gallery"};
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
		builder.setTitle("Select Option");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					dialog.dismiss();

					//
					// this calls takePhoto() after checking for the required permissions (generated)
					//
					AccountProfileActivityPermissionsDispatcher.takePhotoWithCheck(AccountProfileActivity.this);
				} else if (options[item].equals("Choose From Gallery")) {
					dialog.dismiss();

					//
					// this calls pickPhotoFromGallery() after checking for the required permissions (generated)
					//
					AccountProfileActivityPermissionsDispatcher.pickPhotoFromGalleryWithCheck(AccountProfileActivity.this);
				}
			}
		});
		builder.show();
	}

	@NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
	void pickPhotoFromGallery() {
		Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
	}

	@NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
	void takePhoto() {
		try {
			String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
			File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			photoFile = File.createTempFile(
					imageFileName,  /* prefix */
					".jpg",         /* suffix */
					storageDir      /* directory */
			);
		} catch (IOException e) {
			Log.e("AccountProfileActivity", e.getMessage(), e);
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		startActivityForResult(intent, PICK_IMAGE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_IMAGE_CAMERA) {
				presenter.processImage(photoFile.getPath());
			} else if (requestCode == PICK_IMAGE_GALLERY) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA};

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

				String path = cursor.getString(columnIndex);
				cursor.close();

				presenter.processImage(path);
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		AccountProfileActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
	}

	@Override
	public void setEmail(String savedEmail) {
		emailText.setText(savedEmail);
	}

	@Override
	public void setPassword(String password) {
		passwordText.setText(password);
	}

	@Override
	public void loadAvatar(Uri url) {
		Picasso
				.with(this)
				.load(url)
				.placeholder(R.drawable.ic_person_200dp)
				.into(avatarView);
	}

	@Override
	public void loadAvatar(Bitmap bitmap) {
		avatarView.setImageBitmap(bitmap);
	}
}
