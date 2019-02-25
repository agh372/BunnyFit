package com.teamfitness.fitapp.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.TrackingState;
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
import com.teamfitness.fitapp.MainActivity;
import com.teamfitness.fitapp.R;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ARFragment extends Fragment {
    ModelRenderable modelRenderable = null;
    ArFragment arFragment = null;

    public ARFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  arFragment =
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //      arFragment = root.findA(R.id.sceneform_ux_fragment);
        arFragment =
                (ArFragment)
                        getActivity().getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        ((ModelRenderable.Builder)
                ModelRenderable.builder()
                        .setSource(getActivity().getApplicationContext(), Uri.parse("model.sfb")))
                .build()
                .thenAcceptAsync(
                        (Consumer)
                                new Consumer() {
                                    // $FF: synthetic method
                                    // $FF: bridge method
                                    public void accept(Object var1) {
                                        this.accept((ModelRenderable) var1);
                                    }

                                    public final void accept(ModelRenderable modelRenderable) {
                                    }
                                });
        final ArSceneView scene = arFragment.getArSceneView();

        scene
                .getScene()
                .addOnUpdateListener(
                        new Scene.OnUpdateListener() {
                            @Override
                            public void onUpdate(FrameTime frameTime) {

                                Frame frame = scene != null ? scene.getArFrame() : null;
                                if (frame != null) {
                                    Iterator var3 = frame.getUpdatedTrackables(Plane.class).iterator();

                                    while (true) {
                                        Plane plane;
                                        Iterator iterableAnchor;
                                        do {
                                            do {
                                                if (!var3.hasNext()) {
                                                    return;
                                                }

                                                Object var10000 = var3.next();
                                                if (var10000 == null) {
                                                    //  throw new TypeCastException("null cannot be cast to non-null type
                                                    // com.google.ar.core.Plane");
                                                }

                                                plane = (Plane) var10000;
                                            }
                                            while (plane.getTrackingState() != TrackingState.TRACKING);

                                            iterableAnchor = frame.getUpdatedAnchors().iterator();
                                        } while (iterableAnchor.hasNext());

                                        List hitTest = frame.hitTest(screenCenter(frame).x, screenCenter(frame).y);
                                        Iterator hitTestIterator = hitTest.iterator();

                                        while (hitTestIterator.hasNext()) {
                                            HitResult hitResult = (HitResult) hitTestIterator.next();
                                            Anchor modelAnchor = plane.createAnchor(hitResult.getHitPose());
                                            AnchorNode anchorNode = new AnchorNode(modelAnchor);
                                            TransformableNode transformableNode =
                                                    new TransformableNode(
                                                            (TransformationSystem) arFragment.getTransformationSystem());
                                            transformableNode.setParent((NodeParent) anchorNode);
                                            transformableNode.setRenderable((Renderable) modelRenderable);
                                            transformableNode.setWorldPosition(
                                                    new Vector3(
                                                            modelAnchor.getPose().tx(),
                                                            modelAnchor
                                                                    .getPose()
                                                                    .compose(Pose.makeTranslation(0.0F, 0.05F, 0.0F))
                                                                    .ty(),
                                                            modelAnchor.getPose().tz()));
                                        }
                                    }
                                }
                            }
                        });
        return inflater.inflate(R.layout.fragment_ar, container, false);
    }

    private final Vector3 screenCenter(Frame frame) {
        View vw = getActivity().findViewById(android.R.id.content);
        return new Vector3((float) vw.getWidth() / 2.0F, (float) vw.getHeight() / 2.0F, 0.0F);
    }
}
