package linw.stepdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mStepCount;
    private Sensor mStepDetector;
    TextView tvTempCount;
    TextView tvAllCount;

    private float mCount;//步行总数
    private float mDetector;//步行探测器
    private static final int sensorTypeD = Sensor.TYPE_STEP_DETECTOR;
    private static final int sensorTypeC = Sensor.TYPE_STEP_COUNTER;
    private SensorManager mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTempCount = (TextView) findViewById(R.id.tv_step_temp_count);
        tvAllCount = (TextView) findViewById(R.id.tv_step_all_count);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mStepCount = mSensorManager.getDefaultSensor(sensorTypeC);
        mStepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
    }

    public void setSensor(View view) {
        if (view.getId() == R.id.btn_register) {
            mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);
            mSensorManager.registerListener(this, mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
        } else if (view.getId() == R.id.btn_unregister) {
            mSensorManager.unregisterListener(this, mStepDetector);
            mSensorManager.unregisterListener(this, mStepCount);

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensorTypeC) {
            //mCount = event.values[0]//为计步历史累加值
            tvAllCount.setText(event.values[0] + " 步");
        }
        if (event.sensor.getType() == sensorTypeD) {
            if (event.values[0] == 1.0) {
                mDetector++;
                //event.values[0];//一次有效计步数据
                tvTempCount.setText(mDetector + " 步");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
