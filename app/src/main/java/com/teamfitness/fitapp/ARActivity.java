package com.teamfitness.fitapp;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.NodeParent;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.Iterator;
import java.util.List;

public class ARActivity extends AppCompatActivity {

    ArFragment arFragment = null;
    public ModelRenderable mmodelRenderable = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arFragment =
                (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        ArSceneView scene = arFragment.getArSceneView();
        ModelRenderable.builder()
                // get the context of the ARFragment and pass the name of your .sfb file
                .setSource(getBaseContext(), Uri.parse("model.sfb"))
                .build(); // .thenAcceptAsync{ modelRenderable -> this@MainActivity.modelRenderable =
        // modelRenderable }

        Session mSession = null;
        try {
            mSession = new Session(this); // scene.getSession();
        } catch (UnavailableArcoreNotInstalledException e) {
            e.printStackTrace();
        } catch (UnavailableApkTooOldException e) {
            e.printStackTrace();
        } catch (UnavailableSdkTooOldException e) {
            e.printStackTrace();
        } catch (UnavailableDeviceNotCompatibleException e) {
            e.printStackTrace();
        }

        Anchor newAnchor = null;
        for (Plane plane : mSession.getAllTrackables(Plane.class)) {
            if (plane.getType() == Plane.Type.HORIZONTAL_UPWARD_FACING
                    && plane.getTrackingState() == TrackingState.TRACKING) {
                Pose pos = plane.getCenterPose();
                newAnchor = plane.createAnchor(pos);
                break;
            }
        }

        Anchor finalNewAnchor = newAnchor;
        ModelRenderable.builder()
                .setSource(this, Uri.parse("model.sfb"))
                .build()
                .thenAccept(modelRenderable -> addModelToScene(finalNewAnchor, modelRenderable))
                .exceptionally(
                        throwable -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(throwable.getMessage()).show();
                            return null;
                        });

        //        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
        //
        //            Anchor anchor = hitResult.createAnchor();
        //            Log.d("TAG", "onCreate: "+anchor);
        //
        //            ModelRenderable.builder()
        //                    .setSource(this, Uri.parse("model.sfb"))
        //                    .build()
        //                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
        //                    .exceptionally(throwable -> {
        //                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //                        builder.setMessage(throwable.getMessage())
        //                                .show();
        //                        return null;
        //                    });
        //        });

    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        Vector3 vec =
                new Vector3(
                        anchorNode.getLocalPosition().x,
                        anchorNode.getLocalPosition().y - 2.0f,
                        anchorNode.getLocalPosition().z - 2.0f);
        anchorNode.setLocalPosition(vec);
        TransformableNode transformableNode =
                new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }

    private final Vector3 screenCenter(Frame frame) {
        View vw = findViewById(android.R.id.content);
        return new Vector3((float) vw.getWidth() / 2.0F, (float) vw.getHeight() / 2.0F, 0.0F);
    }
}
