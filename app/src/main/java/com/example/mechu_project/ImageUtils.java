package com.example.mechu_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.util.Log;

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    /**
     * 지정된 이름의 이미지 파일을 나타내는 File 객체를 반환
     * 디렉터리가 존재하지 않는 경우, 디렉터리를 생성
     *
     * @param context 컨텍스트
     * @param imageName 이미지 파일 이름
     * @return File 객체
     */
    public static File getImageFile(Context context, String imageName) {
        File directory = new File(context.getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return new File(directory, imageName);
    }

    /**
     * 비트맵을 지정된 이름의 파일로 저장
     *
     * @param context 컨텍스트
     * @param bitmap 저장할 비트맵
     * @param imageName 이미지 파일 이름
     * @return 비트맵이 성공적으로 저장되었으면 true, 그렇지 않으면 false
     */
    public static boolean saveBitmapToFile(Context context, Bitmap bitmap, String imageName) {
        File file = getImageFile(context, imageName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos); // PNG 압축률 설정
            Log.d(TAG, "이미지가 성공적으로 저장되었습니다: " + imageName);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "이미지 저장 실패: " + imageName, e);
            return false;
        }
    }

    /**
     * 지정된 이름의 파일에서 비트맵을 로드
     *
     * @param context 컨텍스트
     * @param imageName 이미지 파일 이름
     * @return 비트맵, 이미지를 로드할 수 없는 경우 null
     */
    public static Bitmap loadBitmapFromFile(Context context, String imageName) {
        File file = getImageFile(context, imageName);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.d(TAG, "이미지가 성공적으로 로드되었습니다: " + imageName);
            return bitmap;
        }
        Log.e(TAG, "이미지 로드 실패: " + imageName);
        return null;
    }
}
