// +build !ignore_autogenerated

// Code generated by operator-sdk. DO NOT EDIT.

package v1alpha1

import (
	runtime "k8s.io/apimachinery/pkg/runtime"
)

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *EnvVariable) DeepCopyInto(out *EnvVariable) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new EnvVariable.
func (in *EnvVariable) DeepCopy() *EnvVariable {
	if in == nil {
		return nil
	}
	out := new(EnvVariable)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *KobeDataset) DeepCopyInto(out *KobeDataset) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ObjectMeta.DeepCopyInto(&out.ObjectMeta)
	in.Spec.DeepCopyInto(&out.Spec)
	in.Status.DeepCopyInto(&out.Status)
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new KobeDataset.
func (in *KobeDataset) DeepCopy() *KobeDataset {
	if in == nil {
		return nil
	}
	out := new(KobeDataset)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *KobeDataset) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *KobeDatasetList) DeepCopyInto(out *KobeDatasetList) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	out.ListMeta = in.ListMeta
	if in.Items != nil {
		in, out := &in.Items, &out.Items
		*out = make([]KobeDataset, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new KobeDatasetList.
func (in *KobeDatasetList) DeepCopy() *KobeDatasetList {
	if in == nil {
		return nil
	}
	out := new(KobeDatasetList)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *KobeDatasetList) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *KobeDatasetSpec) DeepCopyInto(out *KobeDatasetSpec) {
	*out = *in
	if in.EnvVariables != nil {
		in, out := &in.EnvVariables, &out.EnvVariables
		*out = make([]EnvVariable, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new KobeDatasetSpec.
func (in *KobeDatasetSpec) DeepCopy() *KobeDatasetSpec {
	if in == nil {
		return nil
	}
	out := new(KobeDatasetSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *KobeDatasetStatus) DeepCopyInto(out *KobeDatasetStatus) {
	*out = *in
	if in.PodNames != nil {
		in, out := &in.PodNames, &out.PodNames
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new KobeDatasetStatus.
func (in *KobeDatasetStatus) DeepCopy() *KobeDatasetStatus {
	if in == nil {
		return nil
	}
	out := new(KobeDatasetStatus)
	in.DeepCopyInto(out)
	return out
}
