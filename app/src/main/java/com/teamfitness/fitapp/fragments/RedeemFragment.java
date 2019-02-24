package com.teamfitness.fitapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamfitness.fitapp.R;
import com.teamfitness.fitapp.adapter.CouponAdapter;
import com.teamfitness.fitapp.model.Coupon;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RedeemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RedeemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RedeemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView couponRecyclerView;
    private CouponAdapter mCouponAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private OnFragmentInteractionListener mListener;

    public RedeemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RedeemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RedeemFragment newInstance(String param1, String param2) {
        RedeemFragment fragment = new RedeemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_redeem, container, false);
        couponRecyclerView = (RecyclerView) rootView.findViewById(R.id.coupons_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        couponRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        couponRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        Coupon c1 = new Coupon();
        c1.setId(1);
        c1.setImageUrl("state");
        c1.setName("State Farm");
        c1.setPriceDiscount(120);
        c1.setType("Life Insurance");

        Coupon c2 = new Coupon();
        c2.setId(1);
        c2.setImageUrl("health");
        c2.setName("Health Insurance Company");
        c2.setPriceDiscount(100);
        c2.setType("Health Insurance");

        Coupon[] myDataset = {c1, c2, c1 , c2, c1};
        mCouponAdapter = new CouponAdapter(myDataset, getContext());
        couponRecyclerView.setAdapter(mCouponAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
